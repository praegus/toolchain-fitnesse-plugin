# Browser Test

## Configuring the browser

## Configuring the browser

#### Using the driver Setup Fixture
See [Selenium Driver Setup](../selenium-driver-setup)
#### Using the JUnit runner

## Browser commands

### Browser configuration

##### screenshot base directory &lt;directory&gt;
Set the base directory where screenshots will be stored to the provided directory.

##### screenshot show height &lt;height&gt;
Set the height of the screenshot images that are displayed

##### ensure active tab is not closed


##### ensure only one tab


##### seconds before page load timeout &lt;timeout&gt;
Sets the timeout in which the page has to be fully loaded to the value in seconds specified by timeout. After this timeout an exeption is thrown.

##### seconds before page load timeout
Returns the timeout in seconds for which the page has to be fully loaded. After this timeout an exeption is thrown.

##### seconds before timeout &lt;timeout&gt;
Sets the general timeout for browser test to the value in seconds specified by timeout. This is the timeout used by the various actions below, after this timeout an exeption is thrown.

##### seconds before timeout
Returns the general timeout in seconds for browser test.

##### scroll elements to center of viewport
Makes it so that the viewport of the browser is always scrolled to the center of the element on which an action is taken.

##### wait milli second after scroll &lt;msToWait&gt;
Wait msToWait in milliseconds after a scroll action.

##### set continue if ready state interactive &lt;continueIfReadyStateInteractive&gt;


##### is continue if ready state interactive
Returns the value (boolean) of ready state interactive

##### set drag distance &lt;dragDistance&gt;


##### set drag press delay &lt;dragPressDelay&gt;


##### clear drag setup


##### set implicit wait for angular to &lt;implicitWaitForAngular&gt;
Sets the boolean that controls whether BrowserTest should determine whether the site being tested uses AngularJs and if so, wait for Angular to finish rendering the page (default is ‘true’).

##### is implicit wait for angular enabled
Returns the value (boolean) that that controls whether BrowserTest should determine whether the site being tested uses AngularJs and if so, if it should wait for Angular to finish rendering the page.

##### set ng browser test &lt;ngBrowserTest&gt;


##### get ng browser test


##### set trim on normalize &lt;trimOnNormalize&gt;
This command sets a boolean (trimOnNormalize) that determines if 'normalized' functions remove starting and trailing whitespaces

##### trim on normalize
Returns the value of the boolean that determines if 'normalized' functions remove starting and trailing whitespaces

##### use aria table structure &lt;waiAriaTables&gt;
Boolean which tells browser test to expect wai aria style tables made up of divs and spans with roles like table/cell/row/etc.

### General browser commands

##### cookie value &lt;cookieName&gt;
Gets the value of the cookie with the name cookieName.

##### delete all cookies
This will delete all cookies for the domain currently opened in the browser

##### clear local storage
This will delete the local HTML5 storage for the domain currently opened in the browser

##### clear session storage
This will delete the HTML5 session storage for the domain currently opened in the browser

##### current browser height
Gives back the current browser viewport height in pixels
This command can be used as an input for a string, the string can then be used elsewhere in the test execution.

##### current browser width
Gives back the current browser viewport width in pixels
This command can be used as an input for a string, the string can then be used elsewhere in the test execution.

##### set browser height &lt;newHeight&gt;
Sets the browser height to newHeight in pixels.

##### set browser width &lt;newWidth&gt;
Sets the browser width to newWidth in pixels

##### set browser size to &lt;newWidth&gt; by &lt;newHeight&gt;
Sets the browser width to newWidth in pixels and height to newHeight in pixels.

##### set browser size to maximum
Sets the browser size to maximum, maximizes the size of it (full screen)

##### current tab index
Gives back the current tab index (the current tab number).
This command can be used as an input for a string, the string can then be used elsewhere in the test execution.

##### tab count
Gives back the current number of tabs.

##### switch to next tab
Switches to the next tab within the browser.

##### switch to previous tab
Switches to the previous tab within the browser.

##### page content type
Returns the content type of the page currently opened.

##### page source
Returns the raw page source of the page currently opened.
Used in combination with ‘show’ verb, the html is escaped and added to the wiki table.

##### save page source
Saves the raw page source of the page currently opened to the wiki files section and returns a link to the created file.
Used in combination with ‘show’ verb, a link to the created file is added to the wiki table.

##### page title
Returns the title of the page currently opened.

##### switch to default content
Switches to the main/top level (i)frame and makes it the current frame

##### switch to frame &lt;technicalSelector&gt;
Switches to the child frame technicalSelector (a locator) of the current (i)frame and makes it the active frame.

##### switch to parent frame
Switches to the parent frame technicalSelector (a locator) of the current (i)frame and makes it the active frame.

##### set global value &lt;symbolName&gt; to &lt;value&gt;
Sets a (global) value in symbolName to value, so it can be accessed by other fixtures/pages.

##### global value &lt;symbolName&gt;
Returns the value of the (global) value symbolName.

##### set implicit find in frames to &lt;implicitFindInFrames&gt;
Sets the boolean that controls whether BrowserTest should look for elements inside nested (i)frames if they cannot be found in the current frame (default is ‘true’).

##### send command for control on mac


##### set send command for control on mac to &lt;sendCommand&gt;


### Alerts

##### alert text
Return the text of a currently opened Javascript alert

##### confirm alert
Confirm the open JavaScript alert.

##### confirm alert if available
Confirm the open Javascript alert. The command doesn't throw an exception on timeout but instead returns a false.
This prevents a failure in test execution when used with the ‘show’ verb. This is very useful when used as an optional conformation (when you don't know if the alert can be confirmed).
Also can be used with script table’s ‘reject’ verb to check something cannot be confirmed.

##### dismiss alert
Dismiss the open JavaScript alert.

##### dismiss alert if available
Dismiss the open Javascript alert. The command doesn't throw an exception on timeout but instead returns a false.
This prevents a failure in test execution when used with the ‘show’ verb. This is very useful when used as an optional dismissal (when you don't know if the alert can be dismissed).
Also can be used with script table’s ‘reject’ verb to check something cannot be dismissed.

### Navigation

##### back
Simulate a press on the browser's BACK button

##### forward
Simulate a press on the browser's FORWARD button

##### close tab
Closes the tab that is currently open

##### open &lt;url&gt;
Opens the url in the browser

##### open in new tab &lt;url&gt;
Opens the url in a new tab

##### location
Returns the current web address (i.e. URL of the current page)

##### download content from &lt;urlOrLink&gt;
Downloads the file from the url to the wiki’s files section.

##### refresh
Refreshes the page within the browser

### Actions on the page

##### scroll to &lt;place&gt; (in &lt;container&gt;)
Scrolls the viewport of the browser to make sure the place (a locator) is visible.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container gets scrolled into view.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a timeout.

##### scroll elements to center of viewport &lt;scrollElementsToCenterOfViewport&gt;
Scrolls the viewport of the browser to the center of the defined 'scrollElementsToCenterOfViewport' (a locator).

##### set search context to &lt;container&gt;
Limits searches for places by other keywords/commands to inside the container (a locator) instead of the whole page.
This is useful in case the same place occurs multiple times on the same page, to ensure which one is found.
All subsequent searches are limited to ‘container’ until either the ‘clear search context’, ‘refresh’ or ‘open’ command is given.
Many commands also offer an ‘in container’ version (e.g. ‘click place in container’) to limit searching only for that row.

##### refresh search context
Refreshes the search context previously set in case it has gone stale. This is a workaround for when an element previously used for the search context has been replaced.

##### clear search context
Removes any search limits previously set by ‘set search context’: subesquent searches will search the entire page.

##### click &lt;place&gt; (in &lt;container&gt;)
Click on the defined place: this place can be text/link (as visible in the browser), but also a locator (css or xpath).
Optionally you can constrain the click to a container (the 'in' part), this will result that the click happens within the container.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a timeout.

##### click at &lt;place&gt; (offset &lt;xOffset&gt; xy &lt;yOffset&gt;)
Execute a click at the center of the defined place: this place can be text/link (as visible in the browser), but also a locator (css or xpath).
Optionally you can use an offset to click a certain distance (in pixels) from the center of the place.
The offset defines that distance: x-axis followed by y-axis.

##### click if available &lt;place&gt; in &lt;container&gt;
This executes a click on a place is that place is available on the page. The command doesn't throw an exception on timeout but instead returns a false.
This prevents a failure in test execution when used with the ‘show’ verb. This is very useful when used as an optional click: when you need to click an element but you don't know if it is there/available (for instance when dismissing a coocky notification).
Optionally you can constrain the click to a container (the 'in' part), this will result that the click happens within the container.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a false.

##### control click &lt;place&gt; (in &lt;container&gt;)
Simulate a click while holding down the CTRL key on the defined place: this place can be text/link (as visible in the browser), but also a locator (css or xpath).
Optionally you can constrain the control+click to a container (the 'in' part), this will result that the click happens within the container.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a timeout.

##### shift click &lt;place&gt; (in &lt;container&gt;)
Simulate a click while holding down the SHIFT key on the defined place: this place can be text/link (as visible in the browser), but also a locator (css or xpath).
Optionally you can constrain the shift+click to a container (the 'in' part), this will result that the click happens within the container.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a timeout.

##### double click &lt;place&gt; (in &lt;container&gt;)
Simulate a double click on the defined place: this place can be text/link (as visible in the browser), but also a locator (css or xpath).
Optionally you can constrain the double click to a container (the 'in' part), this will result that the click happens within the container.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a timeout.

##### double click at &lt;place&gt; offset &lt;xOffset&gt; xy &lt;yOffset&gt;
Execute a double click at the center of the defined place: this place can be text/link (as visible in the browser), but also a locator (css or xpath).
Optionally you can use an offset to click a certain distance (in pixels) from the center of the place.
The offset defines that distance: x-axis followed by y-axis.

##### right click &lt;place&gt; (in &lt;container&gt;)
Right-click on the defined place: this place can be text/link (as visible in the browser), but also a locator (css or xpath).
Optionally you can constrain the right-click to a container (the 'in' part), this will result that the right-click happens within the container.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a timeout.

##### right click at &lt;place&gt; offset &lt;xOffset&gt; xy &lt;yOffset&gt;
Execute a right-click at the center of the defined place: this place can be text/link (as visible in the browser), but also a locator (css or xpath).
Optionally you can use an offset to right-click a certain distance (in pixels) from the center of the place.
The offset defines that distance: x-axis followed by y-axis.

##### click in row &lt;place&gt; number &lt;rowIndex&gt;
This executes a click in a table defined by place (a locator). The click will be executed on the row number defined by rowIndex.

##### click in row &lt;place&gt; where &lt;selectOnColumn&gt; is &lt;selectOnValue&gt;
This executes a click in a table defined by place (a locator). The click will be executed on the first row of which the selectOnColumn value matches the selectOnValue.

##### clear &lt;place&gt; (in &lt;container&gt;)
This clears the input field defined by place (a locator) of any content, leaving you with an empty input field.
Optionally you can constrain the input field to a container (the 'in' part), this will result that the input field within the container will be cleared.
The container can be defined using a locator. When using a container the input field has to be present within it, otherwise you will get a timeout.

##### drag and drop &lt;source&gt; to &lt;destination&gt;
Drags the source (a locator) to a destination (also a locator)

##### drag and drop to &lt;place&gt; offset &lt;xOffset&gt; xy &lt;yOffset&gt;
Drags to an offset from the middle of the place (also a locator). This offset is defined in pixels.
The offset defines that distance: x-axis followed by y-axis.

##### html 5 drag and drop &lt;source&gt; to &lt;destination&gt;
Drags the source (a locator) to a destination (also a locator) using the HTML5 method.

###### press enter
Simulate a press on the button ENTER within the browser

###### press esc
Simulate a press on the button ESC within the browser

###### press tab
Simulate a press on the button TAB within the browser

##### press &lt;key&gt;
Simulate a press of the mentioned key within the browser.

##### hover over &lt;place&gt; (in &lt;container&gt;)
This command simulates that the mouse hovers over the defined place (a locator). This can be used to test ‘mouseover’ behavior.
Optionally you can constrain the place to a container (the 'in' part), this will result that the field within the container will be hovered over.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### download &lt;place&gt; (in &lt;container&gt;)
Downloads the target of the link from the place (a locator) to the wiki’s files section.
Optionally you can constrain the place to a container (the 'in' part), this will result that the target from the place within the container will be downloaded.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a timeout.

##### download from row &lt;place&gt; number &lt;rowNumber&gt;
This downloads the target of the link in a table defined by place (a locator).
The download will be executed on the link of the row number defined by rowIndex.

##### download from row &lt;place&gt; where &lt;selectOnColumn&gt; is &lt;selectOnValue&gt;
This downloads the target of the link in a table defined by place (a locator).
The download will be executed on the link of the first row of which the selectOnColumn value matches the selectOnValue.

##### wait for class &lt;cssClassName&gt;
Waits until the class with the css selector cssClassName is visible on the page.

##### wait for class with &lt;cssClassName&gt; text &lt;expectedText&gt;
Waits until the class with the css selector cssClassName contains the text expectedText.

##### wait for page &lt;pageTitle&gt;
Waits until a page with the page title pageTitle is shown.

##### wait for tag with &lt;tagName&gt; text &lt;expectedText&gt;
Waits until the tag with tagName contains the text expectedText.

##### wait for visible &lt;place&gt; (in &lt;container&gt;)
Waits until the mentioned place (a locator) is visible on the page.
Optionally you can constrain the place to a container (the 'in' part), this will result that the browser waits until the place within the container is visible.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will eventually get a timeout.

##### wait for x path visible &lt;xPath&gt;
Waits until the xpath defined by xPath is visible.

##### wait milliseconds &lt;i&gt;
Wait i milliseconds

##### wait seconds &lt;i&gt;
Wait i seconds

##### enter &lt;value&gt; as &lt;place&gt; (in &lt;container&gt;)
Clears the place of any current value and types the value above in its place.
Optionally you can constrain the place to a container (the 'in' part), this will result that the value is typed into the place within the container.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a timeout.

##### enter as in &lt;value&gt; row &lt;requestedColumnName&gt; where &lt;selectOnColumn&gt; is &lt;selectOnValue&gt;
This enters the value in the column with header columnName where the first row with header selectColumn matches the value selectValue.

##### enter date &lt;date&gt; as &lt;place&gt;
Replaces the current value of place with the entered date. The input must be of type data (i.e. using the HTML5 datepicker) and must be in the format yyyy-mm-dd.

##### enter &lt;value&gt; for &lt;place&gt; (in &lt;container&gt;)
Adds the value above to the current value of the place (a selector).
Optionally you can constrain the place to a container (the 'in' part), this will result that the value is added to the value of the place within the container.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a timeout.

##### enter for &lt;value&gt; hidden &lt;idOrName&gt;
Set the value above for the hidden input field with id or name 'idOrName'

##### type &lt;text&gt;
Simulate the keyboard to type the text above.

##### select &lt;value&gt; as &lt;place&gt; (in &lt;container&gt;)


##### select file &lt;fileName&gt;
Selects a file using the first file upload control.

##### select file &lt;fileName&gt; for &lt;place&gt; (in &lt;container&gt;)
Selects a file using the first file upload control for the provided place (a locator).
Optionally you can constrain the place to a container (the 'in' part), this will result that the file will be selected for the place within the container.
The container can be defined using a locator. When using a container the place has to be present within it, otherwise you will get a timeout.

##### select &lt;value&gt; for &lt;place&gt; (in &lt;container&gt;)
Select the value for the drop down box or radio buttons located using place (a locator).
Optionally you can constrain the drop down box or radio buttons to a part of the website located using container (the 'in' part). This will result that only the drop down box or radio buttons within the container are used.
The container can be defined using a locator. When using a container the drop down box or radio buttons have to be present within it, otherwise you will get a timeout.

##### execute script &lt;script&gt;
Execute the provided script.

###Validations on the page

##### take screenshot &lt;basename&gt;
Takes a screenshot with the name 'basename' and with extension *.png (used in combination with ‘show’ verb)

##### take screenshot &lt;basename&gt; of &lt;place&gt; (in &lt;container&gt;)
Takes a screenshot with the name 'basename' of the defined place (a locator).
Optionally you can constrain the place to a container (the 'in' part), this will result that the screenshot will be taken of the field within the container.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### available options for &lt;place&gt;
This returns all available options for the dropdown box place (a locator) 

##### text by class name &lt;className&gt;
Returns the text found using the CSS class name 'className'. Warning: this has been depreciated, use value of instead.

##### text by x path &lt;xPath&gt;
Returns the text found using the Xpath found using 'container'. Warning: this has been depreciated, use value of instead.

##### value for &lt;place&gt; (in &lt;container&gt;)
Returns the currently selected value of the dropdown place (a locator).
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for returning the value of the dropdown.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### value of &lt;place&gt; (in &lt;container&gt;)
Return the value of a place (a locator).
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for returning its value.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### value of attribute &lt;attribute&gt; on &lt;place&gt; (in &lt;container&gt;)
Return the value of an attribute of a place (a locator).
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for returning the attribute value.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### value of column number in row &lt;columnIndex&gt; number &lt;rowIndex&gt;
Return the value from column number ‘columnIndex’ in row number ‘rowIndex’

##### value of in row &lt;requestedColumnName&gt; number &lt;rowIndex&gt;
Return the value from the column with header ‘requestedColumnName’ in row number ‘rowIndex’

##### value of in row &lt;requestedColumnName&gt; where &lt;selectOnColumn&gt; is &lt;selectOnValue&gt;
Select the first row where the column with header ‘selectColumn’ is ‘selectValue’ and retrieve the value from the column with header ‘columnName’

##### values for &lt;place&gt; (in &lt;container&gt;)
Returns the selected value of the drop down box or radio buttons located using place (a locator).
Optionally you can constrain the drop down box or radio buttons to a part of the website located using container (the 'in' part). This will result that only the value of the drop down box or radio button within the container are returned.
The container can be defined using a locator. When using a container the drop down box or radio buttons have to be present within it, otherwise you will get a timeout.

##### values of &lt;place&gt; (in &lt;container&gt;)
This retrieves all values of place (a locator). This is expected to be a ‘select’ or a list.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for returning all the values.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### normalized available options for &lt;place&gt;
This returns the normalized values of all available options for the dropdown box place (a locator). These values have all the double spaces and linebreaks (next line or enters) replaced by 1 space.

##### normalized value for &lt;place&gt; (in &lt;container&gt;)
Returns the normalized value of the currently selected value of the dropdown place (a locator). This is the value where all the double spaces and linebreaks (next line or enters) have been replaced by 1 space.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for returning the normalized value of the dropdown.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### normalized value of &lt;place&gt; (in &lt;container&gt;)
Return the normalized value of a place (a locator). This is the value where all the double spaces and linebreaks (next line or enters) have been replaced by 1 space.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for returning its value.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### normalized value of column number in row &lt;columnIndex&gt; number &lt;rowIndex&gt;
Return the normalized value from column number ‘columnIndex’ in row number ‘rowIndex’. This is the value where all the double spaces and linebreaks (next line or enters) have been replaced by 1 space.

##### normalized value of in row &lt;requestedColumnName&gt; number &lt;rowIndex&gt;
Return the normalized value from the column with header ‘requestedColumnName’ in row number ‘rowIndex’. This is the value where all the double spaces and linebreaks (next line or enters) have been replaced by 1 space.

##### normalized value of in row &lt;requestedColumnName&gt; where &lt;selectOnColumn&gt; is &lt;selectOnValue&gt;
Select the first row where the column with header ‘selectColumn’ is ‘selectValue’ and retrieve the normalized value from the column with header ‘columnName’. This is the value where all the double spaces and linebreaks (next line or enters) have been replaced by 1 space.

##### normalized values for &lt;place&gt; (in &lt;container&gt;)
Returns the normalized value of the selected value of the drop down box or radio buttons located using place (a locator). This is the value where all the double spaces and linebreaks (next line or enters) have been replaced by 1 space.
Optionally you can constrain the drop down box or radio buttons to a part of the website located using container (the 'in' part). This will result that only the normalized value of the drop down box or radio button within the container are returned.
The container can be defined using a locator. When using a container the drop down box or radio buttons have to be present within it, otherwise you will get a timeout.

##### normalized values of &lt;place&gt; (in &lt;container&gt;)
This retrieves the normalized values of all values of place (a locator). This is expected to be a ‘select’ or a list. This is the value where all the double spaces and linebreaks (next line or enters) have been replaced by 1 space.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for returning all the normalized values.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### tooltip for &lt;place&gt; (in &lt;container&gt;)
Retrieves the value of the tooltip that is shown when the mouse is placed on the place (a locator).
Optionally you can constrain the place to a container (the 'in' part), this will result that the tooltip will be taken of the field within the container.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### is disabled &lt;place&gt; (in &lt;container&gt;)
Determines if the element found using place (a locator) is disabled (i.e. can NOT be clicked). This can be used in combination with ‘ensure’ and ‘reject’.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for checking if it is disabled.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### is enabled &lt;place&gt; (in &lt;container&gt;)
Determines if the element found using place (a locator) is enabled (i.e. can be clicked). This can be used in combination with ‘ensure’ and ‘reject’.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for checking if it is enabled.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### is not visible &lt;place&gt; (in &lt;container&gt;)
This checks if the place (a locator) is not visible in the browser viewport. This check doesn't take into account loadtimes and such, so make sure that your page is fully loaded and done when using this.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for checking if it is visible.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### is not visible on page &lt;place&gt; (in &lt;container&gt;)
This checks if the place (a locator) is not visible on the page. This check doesn't take into account loadtimes and such, so make sure that your page is fully loaded and done when using this.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for checking if it is visible.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### is visible &lt;place&gt; (in &lt;container&gt;)
This checks if the place (a locator) is visible in the browser viewport.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for checking if it is visible.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### is visible on page &lt;place&gt; (in &lt;container&gt;)
This checks if the place (a locator) is visible on the page.
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for checking if it is visible.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### row exists where &lt;selectOnColumn&gt; is &lt;selectOnValue&gt;
This determines whether there is a row where the column with header ‘selectColumn’ is ‘selectValue’

##### target of link &lt;place&gt;
Returns the link (i.e. the address or URL) of the place (a locator).

##### number for &lt;place&gt; (in &lt;container&gt;)
Returns the number of the item (in a numbered list) located using place (a locator)
Optionally you can constrain the place to a container (the 'in' part), this will result that the place within the container will be used for returning the number.
The container can be defined using a locator. When using a container the field has to be present within it, otherwise you will get a timeout.

##### number of times is visible &lt;text&gt; (in &lt;container&gt;)
Returns the number of times a text is visible within the current browser viewport.
Optionally you can constrain the count to a part of the website located using container (the 'in' part).
This will result that only the text within the container will be used for counting (this is an extra constraint on top of that of the viewport).
The container can be defined using a locator. When using a container, if the container or the text in it is not visible within the browser viewport, the count will return a 0.

##### number of times is visible on page &lt;text&gt; (in &lt;container&gt;)
Returns the number of times a text is visible on the current page.
Optionally you can constrain the count to a part of the website located using container (the 'in' part). This will result that only the text within the container will be used for counting.
The container can be defined using a locator. When using a container, if the container or the text in it is not visible on the page, the count will return a 0.

##### find by technical selector &lt;place&gt; or &lt;supplierF&gt;
Returns the web element found by using either technical selecter 'place' or 'supplierF'.

###Repeating

##### set repeat interval to milliseconds &lt;milliseconds&gt;
Sets the number of milliseconds between requests sent by ‘repeat until ...’ commands.

##### repeat interval
Returns the number of milliseconds between requests sent by ‘repeat until ...’ commands.

##### repeat at most times &lt;maxCount&gt;
Sets the maximum number of requests sent by ‘repeat until ...’ commands. The commands will return false if this number is reached without the condition being met.

##### repeat at most times
Returns the maximum number of requests sent by ‘repeat until ...’ commands.

##### repeat count
Returns the number of requests sent by the last ‘repeat until ...’ command.

##### time spent repeating
Returns the number of milliseconds the last ‘repeat until ...’ command took.

##### refresh until &lt;place&gt; is not visible on page
Refreshes the page within the browser until the place (a locator) is no longer visible on the page.

##### refresh until &lt;place&gt; is visible on page
Refreshes the page within the browser until the place (a locator) is visible on the page.

##### refresh until value of &lt;place&gt; is &lt;expectedValue&gt;
Refreshes the page within the browser until the value of the place (a locator) matches the expectedValue.

##### refresh until value of is &lt;place&gt; not &lt;expectedValue&gt;
Refreshes the page within the browser until the value of the place (a locator) no longer matches the expectedValue.

##### click until value &lt;clickPlace&gt; of &lt;checkPlace&gt; is &lt;expectedValue&gt;
This executes a click on the clickPlace until the value of checkPlace (a locator) matches the value defined by expectedValue

##### click until value of &lt;clickPlace&gt; is &lt;checkPlace&gt; not &lt;expectedValue&gt;
This executes a click on the clickPlace until the value of checkPlace (a locator) no longer matches the value defined by expectedValue

##### execute javascript &lt;script&gt; until &lt;place&gt; is &lt;value&gt;
Execute the javascript 'script' until the value of place (a locator) matches that of the provided value

##### execute javascript until &lt;script&gt; is &lt;place&gt; not &lt;value&gt;
Execute the javascript 'script' until the value of place (a locator) no longer matches that of the provided value

### Selecting

##### select all
Select everything on the page

##### get selection text
Returns the text which is currently selected.

##### cut
Simulates 'cut' (e.g. Ctrl+X on Windows) on the active element, copying the current selection to the clipboard and removing that selection.

##### copy
Simulates 'copy' (e.g. Ctrl+C on Windows) on the active element, copying the current selection to the clipboard.

##### paste
Simulates 'paste' (e.g. Ctrl+V on Windows) on the active element, copying the current clipboard content to the currently active element.