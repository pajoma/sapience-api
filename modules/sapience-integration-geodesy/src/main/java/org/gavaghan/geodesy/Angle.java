/**
 * Copyright 2010 Institute for Geoinformatics (ifgi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * ITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

/* Geodesy by Mike Gavaghan
 * 
 * http://www.gavaghan.org/blog/free-source-code/geodesy-library-vincentys-formula/
 * 
 * This code may be freely used and modified on any personal or professional
 * project.  It comes with no warranty.
 */
package org.gavaghan.geodesy;

/**
 * Utility methods for dealing with angles.
 *  
 * @author Mike Gavaghan
 */
public class Angle
{
   /** Degrees/Radians conversion constant. */
   static private final double PiOver180 = Math.PI / 180.0;
   
   /**
    * Disallow instantiation.
    */
   private Angle()
   {
   }

   /**
    * Convert degrees to radians.
    * @param degrees
    * @return
    */
   static public double toRadians( double degrees )
   {
      return degrees * PiOver180;
   }
   
   /**
    * Convert radians to degrees.
    * @param radians
    * @return
    */
   static public double toDegrees( double radians )
   {
      return radians / PiOver180;
   }
}
