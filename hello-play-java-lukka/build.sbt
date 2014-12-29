import play.Project._

import net.litola.SassPlugin

name := """hello-play-java"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaEbean,
  "mysql" % "mysql-connector-java" % "5.1.26",
  "org.webjars" %% "webjars-play" % "2.2.2", 
  "org.webjars" % "bootstrap" % "2.3.1")

playJavaSettings ++ SassPlugin.sassSettings
