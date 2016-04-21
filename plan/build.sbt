import sbt._
import Process._
import Keys._

lazy val plan = (project in file(".")). 
  settings(
    name := "plan",
    // https://github.com/typesafehub/sbteclipse/wiki/Using-sbteclipse#skipproject
    EclipseKeys.skipProject := true  
 )

