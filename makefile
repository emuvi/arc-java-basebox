all : mvn pkg

mvn :
	mvn clean install
	mvn source:jar install

dps :
	jdeps --module-path build/libBaseBox/ --list-deps build/BaseBox.jar build/libBaseBox/* | grep -E "java\.|javax\.|jdk\."

dpsAll :
	jdeps --module-path build/libBaseBox/ --list-deps build/BaseBox.jar build/libBaseBox/*

bldJre :
	rm -rf jre
	jlink --output jre --add-modules java.base,java.desktop,java.net.http,java.xml,jdk.jfr,jdk.jsobject,jdk.unsupported,jdk.xml.dom --strip-debug --strip-native-commands --compress 2 --no-header-files --no-man-pages

bldJreAll :
	rm -rf jre
	jlink --output jre --add-modules ALL-MODULE-PATH --strip-debug --strip-native-commands --compress 2 --no-header-files --no-man-pages

pkg :
	jpackage --runtime-image jre --input build --dest dist --java-options '--enable-preview' --main-jar BaseBox.jar --name "BaseBox" --app-version 0.1.0 --icon assets/pin/basebox/basebox.ico --win-dir-chooser --win-menu --win-menu-group "Pointel"
