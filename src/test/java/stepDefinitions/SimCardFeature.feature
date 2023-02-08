Feature: Sim Card Activation

  To request sim card info and then send this to an actuator.
  If successful, send this info to the H2 database.
  Query the databse for the saved sim card info.

  # Successful scenario
  Scenario: Successful sim card Activation
  Given the SIM Card Activator is up and running
  When I submit a request to activate the ICCID '1255789453849037777'
  Then I should receive a success response from the microservice
  And when I query the databse for the ICCID '1255789453849037777'
  I should receive a response indicating that the SIM card has been activated successfully

  # Unsuccessful scenario
  Scenario: Failed sim card activation
  Given the REST Controller receives the Sim Card info from the post request
  When the ICCID is sent to the actuator
  Then the actuaor should return that it was successful
  And the sim card should be sent to the database
  When the database is queried for the sim card info
  Then the query should fail and the activation be unsuccessful