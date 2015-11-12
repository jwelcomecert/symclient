///////////////////////////////////////////////////////////////////////////////
//
// This file is a part of the "SampleApp" project which implements all the
// code to demonstate the use of the Symphony API within in a client
// application in a synchronous manner.
//
//
// Copyright International Business Machines Corp, 1992-2013. US Government
// Users Restricted Rights - Use, duplication or disclosure restricted by GSA
// ADP Schedule Contract with IBM Corp. 
// Accelerating Intelligence(TM). All rights reserved. 
//
// This exposed source code is the confidential and proprietary property of
// IBM Corporation. Your right to use is strictly limited by the terms of the
// license agreement entered into with IBM Corporation. 
//
///////////////////////////////////////////////////////////////////////////////

package jonas;
import com.platform.symphony.soam.*;

class SyncClient
{
    public static void main(String args[])
    {
        try
        {
            // Initialize the API
            SoamFactory.initialize();

            // Set up application specific information to be supplied to Symphony
            String appName = "SampleAppJava";

            // Set up application authentication information using the default security provider
            DefaultSecurityCallback securityCB = new DefaultSecurityCallback("Guest", "Guest");

            Connection connection = null;
            try
            {
                // Connect to the specified application
                connection = SoamFactory.connect(appName, securityCB);

                // Retrieve and print our connection ID
                System.out.println("connection ID=" + connection.getId());

                // Set up session attributes
                SessionCreationAttributes attributes = new SessionCreationAttributes();
                attributes.setSessionName("mySession");
                attributes.setSessionType("ShortRunningTasks");
                attributes.setSessionFlags(Session.SYNC);

                // Create a synchronous session
                Session session = null;
                try
                {
                    session = connection.createSession(attributes);

                    // Retrieve and print session ID
                    System.out.println("Session ID:" + session.getId());

                    // Now we will send some messages to our service
                    int tasksToSend = 10;
                    for (int taskCount = 0; taskCount < tasksToSend; taskCount++)
                    {
                        // Create a message
                        MyInput myInput = new MyInput(taskCount, "Hello Grid !!");

                        // Set task submission attributes
                        TaskSubmissionAttributes taskAttr = new TaskSubmissionAttributes();
                        taskAttr.setTaskInput(myInput);

                        // Send it
                        TaskInputHandle input = session.sendTaskInput(taskAttr);

                        // Retrieve and print task ID
                        System.out.println("task submitted with ID : " + input.getId());
                    }

                    // Now get our results - will block here until all tasks retrieved
                    EnumItems enumOutput = session.fetchTaskOutput(tasksToSend);

                    // Inspect results
                    TaskOutputHandle output = enumOutput.getNext();
                    while (output != null)
                    {
                        // Check for success of task
                        if (output.isSuccessful())
                        {
                            // Get the message returned from the service
                            MyOutput myOutput = (MyOutput)output.getTaskOutput();

                            // Display content of reply
                            System.out.println("\nTask Succeeded [" +  output.getId() + "]");
                            System.out.println("Your Internal ID was : " + myOutput.getId());
                            System.out.println("Estimated runtime was recorded as : " + myOutput.getRunTime());
                            System.out.println(myOutput.getString());
                        }
                        else
                        {
                            // Get the exception associated with this task
                            SoamException ex = output.getException();
                            System.out.println("Task Not Successful : ");
                            System.out.println(ex.toString());
                        }
                        output = enumOutput.getNext();
                    }
                }
                finally
                {
                    // Mandatory session close
                    if (session != null)
                    {
                        session.close();
                        System.out.println("Session closed");
                    }
                }
            }
            finally
            {
                // Mandatory connection close
                if (connection != null)
                {
                    connection.close();
                    System.out.println("Connection closed");
                }
            }
        }
        catch (Exception ex)
        {
            // Report exception
            System.out.println("Exception caught:");
            System.out.println(ex.toString());
        }
        finally
        {
            // Uninitialize the API
            // This is the only means to ensure proper shutdown
            // of the interaction between the client and the system.
            SoamFactory.uninitialize();
            System.out.println("All Done !!");
        }
    }
}
