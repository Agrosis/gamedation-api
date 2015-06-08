import sbt._
import sbt.Keys._

import spray.revolver.RevolverPlugin._

object Build extends Build {

  lazy val root = (project in file("."))
    .settings(Revolver.settings: _*)
    .settings(
      name                  := "api",
      organization          := "com.gamedation",
      version               := "0.1.0",
      scalaVersion          := "2.11.6",
      licenses              += ("MIT", url("http://opensource.org/licenses/MIT")),
      scalacOptions         += "-feature",
      scalacOptions         += "-deprecation",
      scalacOptions         += "-unchecked",
      scalacOptions         += "-language:implicitConversions",
      resolvers             ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo),
      resolvers             += "Plasma Conduit Repository" at "http://dl.bintray.com/plasmaconduit/releases",
      libraryDependencies   += "com.plasmaconduit"      %% "plasmaconduit-framework"  % "0.50.0",
      libraryDependencies   += "com.plasmaconduit"      %% "edge"                     % "0.22.0",
      libraryDependencies   += "mysql"                   % "mysql-connector-java"     % "5.1.33",
      libraryDependencies   += "com.typesafe.slick"     %% "slick"                    % "2.1.0",
      libraryDependencies   += "org.flywaydb"            % "flyway-core"              % "3.2.1",
      libraryDependencies   += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
      libraryDependencies   += "com.plasmaconduit"      %% "json-config"              % "0.1.0",
      libraryDependencies   += "org.mindrot"             % "jbcrypt"                  % "0.3m",
      libraryDependencies   += "com.plasmaconduit"      %% "jwt"                      % "0.16.0",
      libraryDependencies   += "commons-codec"           % "commons-codec"            % "1.9"
    )

}
