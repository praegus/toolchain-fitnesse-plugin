# Start the Toolchain
After [setup of a Toolchain project](Set-up-a-toolchain-project.md) is completed. It's time to finally start the Toolchain.
Fasten your seatbelts!

* To start the Toolchain simply select the run configuration and click the run button.

![Run the toolchain](/images/getting-started/run-toolchain.png)

IntelliJ will now use the Maven build tool to download the dependencies, compile the code and run the Toolchain. This
can be seen in the logging. The log also shows us the port the Toolchain is running on. In our case it's running on port ```9090```.

??? tip "Change the port the Toolchain uses"
    If another application is already running on port ```9090``` just change the port the Toolchain uses
    by updating the ```fitnesse.port``` property in the ```pom.xml``` file. This is also very useful if you want to run 
    multiple projects side by side. Just give them each a different port number, and they will run fine! 
    
    ![Change port](/images/getting-started/change-port.png)

![Run log](/images/getting-started/run-log.png)

* To start writing test, open a browser, navigate to http://localhost:9090. The page shown will look like this. You're
ready to write a first tests. 

![Toolchain home](/images/getting-started/fitnesse-home.png)
