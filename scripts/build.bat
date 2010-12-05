javac -d ../build ../src/*.java

cd ../build
jar cvfm ../scripts/PolynomialFactorization.jar ../scripts/manifest *.class
cd ../scripts