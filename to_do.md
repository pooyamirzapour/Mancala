# to do
To develop of the project for the future it should be considered below:

1) Using a non in-memory database such as Mongo DB instead of H2 DB. (The H2 has been chosen
   because is easy to config, and run that is proper for test and assignments.)
   
   
2) For achieving horizontally scaling the ***Map*** in the ***GameEmitterRepository*** class must be changed to the ***Redis***.


3) Implementing a user management module, for registration and login users, and also keeping user's activities, and
   history.
   
   
4) Using Metric and a monitoring system (such as Graylog, Kibana, and Sentry) to capture info, warning, errors, 
   and exceptions are necessary, and it will be set up the relevant console and applications.
   

5) Providing diagrams like Activity, Class, and more needed documents.