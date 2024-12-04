# b07_Project
README
Links
Github: https://github.com/JaydenYip3/b07_Project
Jira: https://potatoritos.atlassian.net/jira/software/projects/BP/boards/1/backlog
Overall code structure
Description of each directory (in src.main.java.com.b07.planetze):
auth
Contains everything related to user authentication
Each screen is written as a fragment (named ...Fragment) connected to AuthActivity
User login / password resetting uses the MVP architectural pattern—see LoginModel (implemented by auth.backend.FirebaseAuthBackend),   LoginPresenter, and LoginView (implemented by AuthActivity)
Tests for this are found in test.java.com.example.planetze.auth.LoginTest 
common
Contains miscellaneous Planetze-related structures that are used by other modules
daily
Contains everything related to daily activities (we refer to these as “dailies” as “activity” is already an Android term)
Key interfaces:
daily.Daily
Represents a logged daily activity
Contains logic for calculating emissions
daily.DailyForm
Represents the forms (the data the user inputs to log an activity) of each daily activity
Contains logic for creating daily.Dailies from forms and vice versa
See the subdirectories of daily for implementations of these interfaces
database
Contains all database operations (see FirebaseDb for the implementation)
ecogauge / ecotracker / ecotracker.habits
the ecogauge / daily activity logging / habit tracking features
form
a programmatic form system used by daily
home
Contains the main navigation menu of the app
onboarding
Contains the onboarding survey
util
Various utilities (most files in here have javadoc)
How to run the code
1. Clone this repository to your local machine using git clone https://github.com/JaydenYip3/b07_Project.git	
2. Open this project in Android Studio or your preferred IDE for Android development.
3. Add Dependencies
Sync the Gradle files to download all necessary dependencies.
4. Connect your Android device or launch an emulator. Build and run the app from Android Studio
Dependencies
Firebase (authentication + realtime database)
This is used for user authentication and data persistence
JUnit
This is used for unit testing
Mockito
This is used for mocking objects
GraphView, MPAndroidChart
This is used for displaying bar/pie charts in the eco gauge
Assumptions
“tons” means “metric tons”
Personalized recommendations for habit filters → display habits of category the user has most emissions is, then sorts by impact
Other
Bonus parts are not implemented
