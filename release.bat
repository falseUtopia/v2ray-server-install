@echo off

echo ������...

set current_dir=%CD%
cd src\main\java\com\falseu\autoinstallproxy
javac.exe -cp "hutool-all-5.8.28.jar;." -encoding UTF-8 -d . AutoInstallProxy.java
java -cp "hutool-all-5.8.28.jar;." com.falseu.autoinstallproxy.AutoInstallProxy release %current_dir%
echo ��������˳�
pause>nul