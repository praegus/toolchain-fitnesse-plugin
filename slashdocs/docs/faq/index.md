# FAQ

!!! info
    Doesn't this page answer your question? And neither do the other docs?
    Create an [issue on Github](https://github.com/praegus/toolchain-fitnesse-plugin/issues)
    
    
!!! faq "I use a method that may return false, but I don't want to fail my test when that happens. How do I do that?"
    Prepend the table row that calls the method with the 'show' keyword. This will result in true/false being displayed in your
    test report, but binds no pass/fail conclusions to it.
    
    Example: `| show | click if available | Accept Cookies |`  
    
    
!!! faq "My test fails because the FitNesse wiki is appending html to the test data I provide"
    Because FitNesse is a Wiki server, it tries to be helpful by providing in-place links for WikiWords (Words starting with
    a capital and then containing one or more capitals. i.e. CamelCaseWikiWord). Also http(s):// links will be made clickable HTML links.
    In a test table, this behaviour is mostly unwanted. You can prevent link generation in a few different ways:
    
    1. Inline escaping using !-...-! (!-CamelCaseWikiWord-!) 
    2. Literal table: prepend your table with ! to switch off any wiki markup in your table. 
    
        Usage: `!|script|my fixture|` Note: this will also disable any symbols like `!today`
        
    3. No Links table (since FitNesse 20201213): prepend your table with ^ to sitch off link generation, but allow symbol rendering
    
        Usage: `^|script|my fixture|` 


!!! faq "I have defined a slim symbol in my SuiteSetUp, but now it's value doesn't show in my test?"
    This behaviour is caused by the client/server architecture of Slim. As both the client and server keep track of symbols, 
    this can get out of sync. When a new test is started, the client has no recollection of previously used symbols. The server however, 
    will not reset its symbol store for the running suite. This means that symbols from previous tests, or SuiteSetUp can be used server-side
    whilst the client has no reference to them. 
    
     - They are available as test data:  `| enter | $mySymbol | as | input |` -> will work 
     - But not as wiki expectation:  `| check | value of | input | $mySymbol |` -> will not work
    
    This behaviour can be (ab)used to hide for example passwords from test reports. Symbols that exist server-side can be restored 
    to the client by re-assigning them using, for example String fixture's value of method:  `| $mySymbol=| value of | $mySymbol |`


## About this toolchain

!!! faq "As a tester, what does this toolchain provide me with?"
    This toolchain makes it easier to create functional- and acceptance tests for web sites and web-API's, e.g. RESTful API's.
    But it also provides tools to test SOAP Web Services, do image oor PDF comparison, interact with files or SFTP servers, etc. 
    The autocomplete FitNesse-responder and the  [Bootstrap Plus Theme](https://github.com/praegus/fitnesse-bootstrap-plus-theme)
    make it easier to develop these tests and en turns your browser into a fully fledged IDE!

## Legal 
!!! faq "Can I use this toolchain to test my commercial software?"
    Yes. All parts of this toolchain are published under the Apache 2.0 or MIT License. These licenses are non-restrictive and 
    allow you to use and redistribute as you may see fit. Please refer to the respective licenses for their exact contents and limitations. 
    
