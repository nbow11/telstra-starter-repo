Feature: Sim Card Activation

  To request sim card info and then send this to an actuator.
  If successful, send this info to the H2 database.
  Query the databse for the saved sim card info.

  # Successful scenario
  Scenario: Successful sim card Activation
  Given the SIM Card Activator is up and running
  When I submit a request to activate the ICCID '1255789453849037777'
  Then I should receive a success response from the microservice

  # Unsuccessful scenario
  Scenario: Failed sim card activation
  Given the SIM Card Activator is up and running
  When I submit a request to activate the ICCID '8944500102198304826'
  Then I should receive a failed response from the microservice