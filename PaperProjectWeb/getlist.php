<?php
$dbhost = 'localhost';
$dbuser = 'root';
$dbpass = '';
$dbname = 'promise_paper_project';
$conn = mysqli_connect($dbhost, $dbuser, $dbpass,$dbname);
if($conn->connect_error ) {
    die('Could not connect: ' . $conn->connect_error);
}
 $doDB = false;
 if(isset($_REQUEST['TABLEID']) && $_REQUEST['TABLEID'] == 88661)
    {   $doDB = true;
        $TABLEID = "courselist";
        $sql_query = "SELECT * FROM $TABLEID";

    }
    if(isset($_REQUEST['TABLEID']) &&  isset($_REQUEST['COURSEID']) && $_REQUEST['TABLEID'] == 88662)
    {
        $COURSEID = $_REQUEST['COURSEID'];
        $doDB = true;
        $TABLEID = "courselist";
        $sql_query = "SELECT * FROM $TABLEID WHERE courseid = $COURSEID";
    }
    if(isset($_REQUEST['TABLEID']) && isset($_REQUEST['SEMVAL']) && isset($_REQUEST['COURSEID']) && $_REQUEST['TABLEID'] == 88663)
    {
        $COURSEID = $_REQUEST['COURSEID'];
        $SEMVAL = $_REQUEST['SEMVAL'];
        $doDB = true;
        $TABLEID = "subjectlist";
        $sql_query = "SELECT * FROM $TABLEID WHERE courseid = $COURSEID AND semval = $SEMVAL";
    }
    if(isset($_REQUEST['TABLEID']) && isset($_REQUEST['SUBJECTID']) && $_REQUEST['TABLEID'] == 88664)
    {   $SUBJECTID = $_REQUEST['SUBJECTID'];
        $doDB = true;
        $TABLEID = "paperlist";
        $sql_query = "SELECT * FROM $TABLEID WHERE subjectid = $SUBJECTID";
    }

if($doDB){
    $result = $conn->query($sql_query);
    $array = array();
    if ($result->num_rows > 0) {
        if($_REQUEST['TABLEID'] == 88661)
        {
            while ($row = $result->fetch_assoc()) {
                $array[] = $row;
            }
            echo "list_fetch_success_88661";
        }
        if($_REQUEST['TABLEID'] == 88662)
        {
            while ($row = $result->fetch_assoc()) {
                $array[] = $row;
            }
            echo "list_fetch_success_88662";
        }
        if($_REQUEST['TABLEID'] == 88663)
        {
            while ($row = $result->fetch_assoc()) {
                $array[] = $row;
            }
            echo "list_fetch_success_88663";
        }
        if($_REQUEST['TABLEID'] == 88664)
        {
            while ($row = $result->fetch_assoc()) {
                $subid = $row['subjectid'];
                $subsql_query = "SELECT * FROM subjectlist WHERE subjectid = $subid";
                $subresult = $conn->query($subsql_query);
                $subrow = $subresult->fetch_assoc();
                $row['subjectname'] = $subrow['subjectname'];
                $row['semval'] = $subrow['semval'];
                $row['courseid'] = $subrow['courseid'];
                $crsid = $subrow['courseid'];
                $subsql_query = "SELECT * FROM courselist WHERE courseid =$crsid";
                $subresult = $conn->query($subsql_query);
                $subrow = $subresult->fetch_assoc();
                $row['coursename'] = $subrow['coursename'];
                $row['semstart'] = $subrow['semstart'];
                $row['semend'] = $subrow['semend'];
                $array[] = $row;
            }
            echo "list_fetch_success_88664";
        }

        echo json_encode($array);
    }
    else if($result->num_rows == 0) {
        echo "listempty";
    }
}
else {
    echo "unsuccessful";
}
mysqli_close($conn);
?>