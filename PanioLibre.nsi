;--------------------------------
;Incluimos el Modern UI
	!include "MUI2.nsh"
;--------------------------------
;Propiedades de la interfaz
	!define MUI_ABORTWARNING
	!define NOMBREAPP "PanioLibre"
;--------------------------------
#General

	;Nombre de la aplicación y del ejecutable
	Name "${NOMBREAPP}"
	Icon "martillo.ico"
	OutFile "PanioLibre.exe"

	;Directorio de instalación
	DirText "Elija un directorio donde instalar la aplicación:"
	InstallDir "$PROGRAMFILES\${NOMBREAPP}"

	;Obtenemos el directorio del registro (si esta disponible)
	InstallDirRegKey HKCU "Software\PanioLibre" ""
	  
	;Indicamos que cuando la instalación se complete no se cierre el instalador automáticamente
	AutoCloseWindow false

	;Si se encuentran archivos existentes se sobreescriben
	SetOverwrite on
	SetDatablockOptimize on
;--------------------------------
#Paginas
	;páginas referentes al instalador
	!insertmacro MUI_PAGE_COMPONENTS
	!insertmacro MUI_PAGE_DIRECTORY
	!insertmacro MUI_PAGE_INSTFILES

	;páginas referentes al desinstalador
	!insertmacro MUI_UNPAGE_CONFIRM
	!insertmacro MUI_UNPAGE_INSTFILES
;--------------------------------
#Lenguajes
	;Definimos el idioma del instalador
	!insertmacro MUI_LANGUAGE "Spanish"
;--------------------------------
#Secciones
Section "PanioLibre" panioLibre

	SetOutPath "$INSTDIR"
	;Hacemos que esta seccion tenga que instalarse obligatoriamente
	SectionIn RO 

	;Incluimos todos los archivos que componen nuestra aplicación

	;Archivos a instalar (solo archivos, los ejecutables van en la sección "prerequisitos"
	File panioLibre.jar
	File "martillo.ico"

	 SetOutPath "$INSTDIR\reportes"
	 File "reportes\ReporteHerramienta.jasper"
	 File "reportes\ReporteInsumo.jasper"
	 File "reportes\ReporteOrdenesDeTrabajo.jasper"
	 SetOutPath "$INSTDIR"

	SetOutPath "$INSTDIR\configuration"
	File "configuration\db.properties"
	File "configuration\mantis.properties"
	SetOutPath "$INSTDIR"

	SetOutPath "$INSTDIR\lib"
	File "lib\commons-beanutils-1.9.0.jar"
	File "lib\commons-collections-3.2.1.jar"
	File "lib\commons-digester-2.1.jar"
	File "lib\commons-logging-1.1.1.jar"
	File "lib\controlsfx-8.40.13.jar"
	File "lib\hamcrest-core-1.3.jar"
	File "lib\jasperreports-6.0.0.jar"
	File "lib\javax.mail-1.6.1.jar"
	File "lib\jcommon-1.0.23.jar"
	File "lib\jfoenix-8.0.4.jar"
	File "lib\jfreechart-1.0.19.jar"
	File "lib\mysql-connector-java-5.1.6.jar"
	File "lib\mantis.conector-0.3.jar"
	SetOutPath "$INSTDIR"
	
	# Make the directory "$INSTDIR" read write accessible by all users
	AccessControl::GrantOnFile "$INSTDIR" "(BU)" "GenericRead + GenericWrite"
	
	;Menu inicio
	SetShellVarContext all
	createDirectory "$SMPROGRAMS\${NOMBREAPP}"
	createShortCut "$SMPROGRAMS\${NOMBREAPP}\${NOMBREAPP}.lnk" "$INSTDIR\panioLibre.jar" "" "$INSTDIR\martillo.ico"
	createShortCut "$SMPROGRAMS\${NOMBREAPP}\ManualDeUsuarioPanioLibre.lnk" "$INSTDIR\ManualDeUsuarioPanioLibre.pdf" "" ""
	createShortCut "$SMPROGRAMS\${NOMBREAPP}\Desinstalar.lnk" "$INSTDIR\Uninstall.exe" "" ""
	    
	;Acceso directo en el escritorio
	CreateShortCut "$DESKTOP\${NOMBREAPP}.lnk" "$INSTDIR\${NOMBREAPP}.jar" "" "$INSTDIR\martillo.ico"
	  
	;Hacemos que la instalación se realice para todos los usuarios del sistema
	SetShellVarContext all

	;Guardamos un registro de la carpeta instalada
	WriteRegStr HKCU "Software\PanioLibre" "" $INSTDIR
	  
	;Creamos un desintalador
	WriteUninstaller "$INSTDIR\Uninstall.exe"

SectionEnd
;--------------------------------
#Seccion desinstalador
Section "Uninstall"

	SetShellVarContext all

	;Borramos el ejecutable del menú inicio
	delete "$SMPROGRAMS\${NOMBREAPP}\${NOMBREAPP}.lnk"
	delete "$SMPROGRAMS\${NOMBREAPP}\ManualDeUsuarioPanioLibre.lnk"
	delete "$SMPROGRAMS\${NOMBREAPP}\Desinstalar.lnk"

	;Borramos el acceso directo del escritorio
	delete "$DESKTOP\${NOMBREAPP}.lnk"

	;Intentamos borrar el menú inicio (Solo se puede hacer si la carpeta está vacío)
	rmDir "$SMPROGRAMS\${NOMBREAPP}"
	 
	;Archivos a desinstalar
	delete $INSTDIR\uninstall.exe
	RMDir /r $INSTDIR
	RMDir /r $APPDATA\Roaming\PanioLibre
	DeleteRegKey /ifempty HKCU "PanioLibre"

SectionEnd
;--------------------------------
#Seccion Prerequisitos, ejecucion de otros instaladores 
Section "Prerequisitos" prerequisitos

	SectionIn RO
	DetailPrint "Comenzando la instalacion de Java"
		SetOutPath $TEMP
	    File "dependencias\jre-8u161-windows-i586.exe"
	    ExecWait '"$TEMP\jre-8u161-windows-i586.exe" /s'
	DetailPrint "Comenzando la instalacion de Mysql Server"
	    SetOutPath $TEMP     
	    File "dependencias\mariadb-10.2.14-win32.msi"    
	    ExecWait 'msiexec /i $TEMP\mariadb-10.2.14-win32.msi ADDLOCAL=Client /qn'   
SectionEnd

;--------------------------------
;Seccion "Servidor MySQL"
 Section "Servidor de MySQL" server

		DetailPrint "Comenzando la instalacion de Mysql Server"
	    SetOutPath $TEMP     
	    File "dependencias\mariadb-10.2.14-win32.msi"    
	    ExecWait 'msiexec /i $TEMP\mariadb-10.2.14-win32.msi SERVICENAME=mariadb10 PASSWORD=root ADDLOCAL=DBInstance,MYSQLSERVER /qn'
	    
	    File "sql\PanioLibre.sql"
	    #Obtener el path de la consola y almacenarlo en $0
	    ExpandEnvStrings $0 %COMSPEC%
		ExecWait '"$0" /C "$PROGRAMFILES\Mariadb 10.2\bin\mysql" -u root -proot < $TEMP\PanioLibre.sql'

		File "sql\insercionDatosBase.sql"
	    #Obtener el path de la consola y almacenarlo en $0
	    ExpandEnvStrings $0 %COMSPEC%
		ExecWait '"$0" /C "$PROGRAMFILES\Mariadb 10.2\bin\mysql" -u root -proot < $TEMP\insercionDatosBase.sql'

SectionEnd
;--------------------------------
;Seccion "Manual de usuario"
 Section "Manual de usuario" manual

	SetOutPath "$INSTDIR"
	;Archivos a instalar
	  File ManualDeUsuarioPanioLibre.pdf

SectionEnd
;--------------------------------
;Seccion "Insertar datos de Prueba"
 Section "Insertar datos de prueba" insert

 	DetailPrint "Comenzando la insercion de datos de Prueba"
	SetOutPath $TEMP     
	File "sql\insercionDatos.sql"
	#Obtener el path de la consola y almacenarlo en $0
	ExpandEnvStrings $0 %COMSPEC%
	ExecWait '"$0" /C "$PROGRAMFILES\Mariadb 10.2\bin\mysql" -u root -proot < $TEMP\insercionDatos.sql'

SectionEnd
;--------------------------------
#Descripciones

  ;Descripcion de PanioLibre
  LangString DESC_PanioLibre ${LANG_SPANISH} "Archivos necesarios para la ejecución de la PanioLibre"

  ;Descripcion de Prerequisitos
  LangString DESC_Prerequisitos ${LANG_SPANISH} "Archivos necesarios para que PanioLibre funcione correctamente"

  ;Descripcion de Servidor MySQL
  LangString DESC_Server ${LANG_SPANISH} "Instalación del Servidor y base de datos del software"
  
  ;Descripcion de Manual
  LangString DESC_Manual ${LANG_SPANISH} "Manual de usuario para el sistema Pañolibre"

  ;Descripcion de Insert
  LangString DESC_Insert ${LANG_SPANISH} "Inserción de datos de Prueba en Base de Datos"

  ;Asignamos las descripciones a cada seccion
  !insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
    !insertmacro MUI_DESCRIPTION_TEXT ${PanioLibre} $(DESC_PanioLibre)
    !insertmacro MUI_DESCRIPTION_TEXT ${Prerequisitos} $(DESC_Prerequisitos)
    !insertmacro MUI_DESCRIPTION_TEXT ${Server} $(DESC_Server)
    !insertmacro MUI_DESCRIPTION_TEXT ${Manual} $(DESC_Manual)
    !insertmacro MUI_DESCRIPTION_TEXT ${Insert} $(DESC_Insert)
  !insertmacro MUI_FUNCTION_DESCRIPTION_END
;--------------------------------