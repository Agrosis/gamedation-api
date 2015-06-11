assemblyOutputPath in assembly := file("/srv/gamedation/api.jar")

assemblyMergeStrategy in assembly := {
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.rename
  case x if x.contains("org/apache/commons/collections/") => MergeStrategy.rename
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}