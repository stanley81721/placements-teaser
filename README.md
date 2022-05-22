# Placements-teaser
Placements.IO Interview Project

[Demo](https://rocky-basin-57012.herokuapp.com/)

# Installation
*Please install [JDK version 11.0.15](https://www.oracle.com/tw/java/technologies/javase/jdk11-archive-downloads.html) before you start the installation.*

1. Install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows) as our IDE.
2. Clone the repo.
3. Open IntelliJ IDEA and load the project.(Maven will install the dependencies at the first time, it may take minutes.)  
   - **Caution** : If you find some dependencies are missing in pom.xml. From the preference in IntelliJ(Top right of the view, choose settings), navigate to `Build, Execution, Deployment -> Build Tools -> Maven` check the `Use plugin registry` and click OK.  
   - Then `File -> Invalidate Caches -> Invalidate and Restart`
4. Make sure the project settings and platform settings are all assigned to Java 11.(You may find the setting on top right of the view. Choose `Project Structure`)
5. Also make sure the compiler setting is assigned to Java 11.(The same place of the view. Choose settings. `Build, Execution, Deployment -> Compiler -> Java Compiler`)
6. Set Run Configurations for the project by click the dropdown list next to Run button, choose `com.interview.PlacementsteaserApplication` as main class.

# Start Running
1. Click "Run" on the top right
2. Open your browser and go to `localhost:8080`