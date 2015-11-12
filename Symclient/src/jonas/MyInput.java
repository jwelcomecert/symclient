///////////////////////////////////////////////////////////////////////////////
//
// This file is a part of the "SampleApp" project which implements all the
// common classes shared by the client and service samples.
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
import java.io.Serializable;


/////////////////////////////////
// Input Data Object
/////////////////////////////////
public class MyInput implements Serializable
{
    //=========================================================================
    //  Constructors
    //=========================================================================

    public MyInput()
    {
        super();
        m_id = 0;
    }

    public MyInput(int id, String string)
    {
        super();
        m_id = id;
        m_string = string;
    }


    //=========================================================================
    //  Accessors and Mutators
    //=========================================================================
    
    public int getId()
    {
        return m_id;
    }

    public void setId(int id)
    {
        m_id = id;
    }

    public String getString()
    {
        return m_string;
    }
    
    public void setString(String string)
    {
        m_string = string;
    }


    //=========================================================================
    //  Private Member Variables
    //=========================================================================

    private int m_id;
    private String m_string;
    private static final long serialVersionUID = 1L;
}
