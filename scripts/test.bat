javac -d ../build ../src/*.java

cd ../build
jar cvfm ../scripts/PolynomialFactorization_test.jar ../scripts/manifest_test *.class
cd ../scripts

java -ea -jar PolynomialFactorization_test.jar

pause