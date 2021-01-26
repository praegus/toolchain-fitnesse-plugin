Package to import: _nl.praegus.fitnesse.slim.fixtures_

A Fixture to interact with GMail via OAuth. Supports sending and receiving emails, inbox filtering and data/attachment extraction.

!!! warning "Warning"
    **NEVER** use this fixture with your private or company Google account! Always create a test account

## Google Account configuration

 - Navigate to <a href="https://console.developers.google.com/cloud-resource-manager" target="_blank">the Google API Console</a>
 and login using the Google account you want to use for testing.
 - Create a project (i.e. toolchain-fixture)
 - Navigate to <a href="https://console.developers.google.com/apis/dashboard" target="_blank">Google API Dahboard</a>
 - Make sure the project you just created is selected and click _ENABLE APIS AND SERVICES_
 - Lookup the Gmail API, select it and click _Enable_
 - Click the _CREATE CREDENTIALS_ button on the top right
 - Select _Gmail API_ for 'Which API are you using?'
 - Select _Other UI_ for 'Where will you be calling the API from?'
 - Select _User data_ for 'What data will you be accessing?'
 - Now click the button _What credentials do I need?_
 - A message will appear about setting up an OAuth consent screen, click _SET UP CONSENT SCREEN_
 - The consent screen setup appears in a new tab/window
 - Select _External_ for 'User Type' (If you use GSuite, you can also choose Internal)
 - Click _CREATE_
 - Enter an app name (i.e. fitnesse)
 - Select you email address for 'User support email'
 - Enter any email address for 'Developer contact infomation'
 - When asked to create test users, add your test e-mail address
 - Click _SAVE AND CONTINUE_ for every step until you get to the Summary
 - Now return to the 'Credentials wizard tab'
 - Enter a name for the OAuth 2.0 client ID (i.e. toolchain-fixture)
 - Click _Create Client ID_
 - Click _Download_ to get the credentials json (client_id.json) or choose to 'Do this later'
 - Click the button to return to the Credentials screen
 - Click the download arrow on the right of the Client ID you just created. This will download the client_secret.
 - Save the client secret to `/src/main/resources` in your FitNesse project and rename it to: `gmail_client_secret.json`
 - Now restart FitNesse.
 
To complete configuration and allow your tests to interact with the GMail  account, paste the following wiki page in a new Test page
and execute it. Please replace toolchain-fixture with the application name you chose above.

??? info "Activation script"
    ```
    !define TEST_SYSTEM {slim}
    !path fixtures/*.jar
    
    |Import                                |
    |nl.praegus.fitnesse.slim.fixtures     |
    
    |script                    |gmail oauth fixture|fitnesse|
    ```

When the above script is executed, your default browser will present a login screen. Log in to create a credential in 
FitNesseRoot/files/gmailOauthFixture/.credentials

Add this credential to your project to be able to run tests that interact with GMail in your CI build.

!!! warning "Warning"
    Please make sure not to commit your credentials to any public repository. Also remember never to use a personal or business e-mail
    address with this fixture. 

### Using multiple accounts
Follow the configuration steps above. When saving `gmail_client_secret.json`, please add a postfix to the file name (`gmail_client_fixture_[postfix].json`)

Now, activate the account with an extra instruction, providing the postfix:
```
|script                    |gmail oauth fixture|fitnesse|
|set client secret postfix |postfix                     |
```

## Methods
### Fixture configuration

#### set client secret postfix (postfix)
Set the postfix to use for the account

#### set repeat interval to (millis) milliseconds
Sets the polling interval when polling for messages to arrive.

#### repeat at most (n) times
Set the maximum numer of times the fixture may poll for messages to arrive

#### set message format (plain/html)
The expected message format (defaults to plain)

### Receiving e-mail

#### set filter query (query)
Set the filter for the inbox to select. For information on all possibel search operators, please refer to  
<a href="https://support.google.com/mail/answer/7190" _target="blank">Google's documentation</a> on this subject.

#### poll until message arrives
Execute the search filter until it fetches at least one message, or the maximum number of repeats depletes.

#### latest message body contains (needle)
Returns _true_ if the latest message that matches your filter contains the given _needle_ string.

#### latest message attachments
Returns a list of attachment file names from the latest message that matches your filter.

#### save latest email body (filename)
Saves the body of the latest message matching your filter and returns a link to the created file.

#### get link containing (search string)
Returns a link containing the search string if such a link is present in the latest message that matches your filter.
Useful for retrieving generated links (i.e. when testing a password reset flow)

#### delete current message
Deletes the latest message matching your filter.

#### trash current messag
Moves the latest message matching you filter to the trash folder (where it will be deleted after 30 days)

### Sending e-mail
#### send email (email message)
Sends the provided email. The email is expected to be a hashmap containing keys:
 - to
 - from
 - subject
 - body
 - attachment (optional)

This map can be constructed in FitNesse using HashTable notation:
```
!{to:!-test@mail.com-!, from:!-me@mail.com-!, subject: Test Message, body: email body text, attachment: http://files/my-attachment.pdf}
```

Or using [Map Fixture](/fixtures/hsac-fixtures/map-fixture/):
```
|script        |map fixture                              |
|set value     |!-test@praegus.nl-!       |for|to        |
|set value     |!-test-account@gmail.com-!|for|from      |
|set value     |mail with attachment      |for|subject   |
|set value     |look at that image!       |for|body      |
|set value     |http://files/an_image.png |for|attachment|
|$emailMessage=|copy map                                 |
```