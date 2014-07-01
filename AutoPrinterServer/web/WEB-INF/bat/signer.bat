@echo off
rem please make sure the jarsigner is in your java bin path
for /r %%c in (*.jar) do (
 echo %%c 
 call cmd /c jarsigner -keystore mc.keystore -storepass zaq12wsx -keypass zaq12wsx %%c  mc_localhost
 call cmd /c jarsigner -keystore mc.keystore -storepass zaq12wsx -keypass zaq12wsx %%c  mc_127.0.0.1 
)
