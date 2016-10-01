//package sapience.lookup.filesystem;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FilenameFilter;
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.nio.CharBuffer;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//import sapience.injectors.Extractor;
//import sapience.injectors.model.contentclue.ContentClueReader;
//import sapience.lookup.LookupImpl;
//import sapience.lookup.LookupImpl_old;
//import sapience.lookup.exceptions.WrongDirectoryException;
//import sapience.lookup.exceptions.WrongFileException;
//
//
///**
// * This class works as a Thread and will be started by the Lookup component.
// * The first call of the Lookup component initiates this Thread, too.
// * The ConsitencyChecker finishs with the end of the Lookup.
// * 
// * The ConsistencyChecker checks every minute a given filesystem.
// * If there is change (new file or file deleted), this component updates the contentClue list
// * of the Lookup.
// * The path of the filesystem can be set easily via the fs.properties file.
// * This component stores only the path and the contentClue of the files in a very performant HashMap.
// * 
// * @author Henry
// *
// */
//
//@Deprecated
//public class ConsistencyCheckerImpl extends Thread implements ConsistencyChecker {
//	
//	public enum Extensions{
//		WSDL
//	}
//	
//	private boolean running;
//	
//	// Path of the filesystem
//	private String fileSystemPath = FileSystemProperties.getString("FileSystem.Path");
//	
//	private LookupImpl_old lookup;
//	
//	private File annotatedFileDirectory;
//	
//	// Storage of the given files by using a HashMap
//	private HashMap<String, CharBuffer> annotatedFiles;
//	
//	// A Lock for the HashMap, so synchronization is given
//	private final Lock lock = new ReentrantLock();
//	
//	private ContentClueReader contentClueReader;
//	
//	/**
//	 * This constructor gets the instance of the Lookup starting it.
//	 * Afterwards the correctness of the given path of the filesystem is proved.
//	 * Finally the Thread will be started.
//	 * 
//	 * @param lookup
//	 * @throws WrongDirectoryException
//	 */
//	public ConsistencyCheckerImpl(LookupImpl_old lookup) throws WrongDirectoryException{
//		this.lookup = lookup;
//		annotatedFileDirectory = new File(fileSystemPath);
//		if (!annotatedFileDirectory.isDirectory()) {
//			throw new WrongDirectoryException(fileSystemPath);
//		}
//		annotatedFiles = new HashMap<String, CharBuffer>();
//		this.running = true;
//		this.start();
//	}
//
//	/* (non-Javadoc)
//	 * @see java.lang.Thread#run()
//	 */
//	@Override
//	public void run() {
//		File[] filesInDirectory;
//		CharBuffer contentClue;
//		
//		// A temporary HashMap 
//		HashMap<String, CharBuffer> annotatedFilesTemp;
//		while (running) {
//			
//			// Lock
//			lock.lock();
//			
//			// Only files, no directories (written in that way, because of .svn folder in 
//			// the tested filesystem
//			filesInDirectory = annotatedFileDirectory.listFiles(new FilenameFilter() {
//				  public boolean accept( File d, String name ) {
//					    return d.isDirectory();
//					  } });
//			
//			// fill the temp HashMap with data
//			annotatedFilesTemp = new HashMap<String, CharBuffer>(annotatedFiles);
//			annotatedFiles.clear();
//			
//			// check whether new files are found in the filesystem or whether files are deleted.
//			for (int i = 0; i < filesInDirectory.length; i++) {
//				try {
//					setContentClueReader(filesInDirectory[i].getName());
//					contentClue = contentClueReader.getContentClue(filesInDirectory[i]);
//					if (!annotatedFilesTemp.containsKey(filesInDirectory[i].getAbsolutePath())) {
//						lookup.listClues().add(contentClue);
//					} else{
//						annotatedFilesTemp.remove(filesInDirectory[i].getAbsolutePath());
//					}
//					annotatedFiles.put(filesInDirectory[i].getAbsolutePath(), contentClueReader.getContentClue(filesInDirectory[i]));
//				} catch (WrongFileException e) {
//					filesInDirectory[i].delete();
//				} catch (Exception e) {
//					//TODO Idea to handle this Exception
//					e.printStackTrace();
//				}
//			}
//			
//			// Unlock
//			lock.unlock();
//			
//			// if files were deleted, the "dazugehörigen" contenClues in the Lookup will be deleted.
//			if (!annotatedFilesTemp.isEmpty()) {
//				ArrayList<CharBuffer> offcut = (ArrayList<CharBuffer>) annotatedFilesTemp.values();
//				for (int i = 0; i < offcut.size(); i++) {
//					
//					// TODO: check if this works (note that remove works by object id, not content
//					lookup.listClues().remove(offcut.get(i));
//				}
//			}
//			annotatedFilesTemp = null;
//			
//			// wait 1 minute for the next check.
//			try {
//				this.sleep(60000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @param fileName
//	 * @throws WrongFileException 
//	 */
//	private void setContentClueReader(String fileName) throws WrongFileException {
//		Extensions extension = Extensions.valueOf(fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()).toUpperCase());
//		switch (extension) {
//		case WSDL:
////			contentClueReader = new WSDLContentClueReader();
//			break;
//
//		default:
//			throw new WrongFileException();
//		}
//	}
//
//	/**
//	 * A Method to get keys from a HashMap by giving a value
//	 * This is used to get the path of a file by giving the contentClue
//	 * This function is needed by the "update" method because the method "storeAnnotations"
//	 * of the Injectors needs a file.
//	 * @param value
//	 * @return
//	 */
//	private List<String> getKeysFromValue(String value){
//	    List <String>list = new ArrayList<String>();
//	    
//	    //Lock
//	    lock.lock();
//	    for(String key:annotatedFiles.keySet()){
//	        if(annotatedFiles.get(key).equals(value)) {
//	            list.add(key);
//	        }
//	    }
//	    
//	    //Unlock
//	    lock.unlock();
//	    return list;
//	}
//
//	/* (non-Javadoc)
//	 * @see sapience.annotations.lookup.filesystem.ConsistencyChecker#update(java.lang.String, java.nio.CharBuffer, sapience.injectors.Injector)
//	 */
//	public void update(String docID, CharBuffer contentClue, Extractor extractor) {
//		ArrayList<String> storeActions = (ArrayList<String>) getKeysFromValue(contentClue.toString());
//		for (int i = 0; i < storeActions.size(); i++) {
//			try {
//				extractor.storeAnnotation(new URI(docID), new FileInputStream(new File(storeActions.get(i))));
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (URISyntaxException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	
//}
