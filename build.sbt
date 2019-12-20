organization := "lt.dvim.polyfact"
name := "polyfact"

autoScalaLibrary := false
crossPaths := false

resolvers in ThisBuild += Resolver.jcenterRepo
libraryDependencies ++= Seq(
  "net.aichler" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test
)

organizationName := "Martynas Mickevičius"
startYear := Some(2010)
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
homepage := Some(url("https://github.com/2m/polyfact"))
scmInfo := Some(ScmInfo(url("https://github.com/2m/polyfact"), "git@github.com:2m/polyfact.git"))
developers += Developer(
  "contributors",
  "Contributors",
  "https://gitter.im/2m/polyfact",
  url("https://github.com/2m/polyfact/graphs/contributors")
)

enablePlugins(AutomateHeaderPlugin)
