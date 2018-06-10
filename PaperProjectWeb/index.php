<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
  <meta charset="UTF-8">
  <title>Paper project</title>
      <link rel="stylesheet" href="new.css">
</head>
<body>
<form action="" method="post">
    <input type="submit" id="reload" value="Reload">
</form>

<?php
$dbhost = 'localhost';
$dbuser = 'root';
$dbpass = '';
$dbname = 'promise_paper_project';
$conn = mysqli_connect($dbhost, $dbuser, $dbpass,$dbname);
if($conn->connect_error ) {
    die('Could not connect: ' . $conn->connect_error);
}
else
{
    echo "<p style=\"color: green; margin: 20px\" >Connected</p>";
}

?>
<!--TESTING_PURPOSE-->
<!--<table id="postcont">-->
<!--    --><?php
//
//
//    foreach ($_POST as $key => $value) {
//        echo "<tr id=\"postcont\">";
//        echo "<td  id=\"postcont\">";
//        echo $key;
//        echo "</td>";
//        echo "<td>";
//        echo $value;
//        echo "</td>";
//        echo "</tr>";
//    }
//
//
//    ?>
<!--</table>-->
<?php
if(isset($_REQUEST['$semstart']))
{
    echo "<p id=\"errortext\">";
    echo $_REQUEST['$errortext'];
    echo "</p>";
}
if(isset($_REQUEST['$succtext']))
{
    echo "<p id=\"succtext\">";
    echo $_REQUEST['$succtext'];
    echo "</p>";
}
?>

<h1>Paper management</h1>
<!--FOR_WORKING_OF_CHECKED_RADIO_BUTTONS-->
<?php
if(!isset($_REQUEST['clickedvar']))
{
    echo "<input id=\"tab1\" type=\"radio\" name=\"tabs\">
<label for=\"tab1\">Add course</label>";
}
else
{
    echo "<input id=\"tab1\" type=\"radio\" name=\"tabs\" checked><label for=\"tab1\">Add course</label>";
}
if(!isset($_REQUEST['clickedvar2']))
{
    echo "<input id=\"tab2\" type=\"radio\" name=\"tabs\">
<label for=\"tab2\">Add subject</label>";
}
else
{
    echo "<input id=\"tab2\" type=\"radio\" name=\"tabs\" checked><label for=\"tab2\">Add subject</label>";
}
if(!isset($_REQUEST['clickedvar3']))
{
    echo "<input id=\"tab3\" type=\"radio\" name=\"tabs\">
<label for=\"tab3\">Add paper</label>";
}
else
{
    echo "<input id=\"tab3\" type=\"radio\" name=\"tabs\" checked><label for=\"tab3\">Add paper</label>";
}
if(!isset($_REQUEST['clickedvar4']))
{
    echo "<input id=\"tab4\" type=\"radio\" name=\"tabs\">
<label for=\"tab4\">Make paper</label>";
}
else
{
    echo "<input id=\"tab4\" type=\"radio\" name=\"tabs\" checked><label for=\"tab4\">Make paper</label>";
}
?>
 <section id="content1">
     <form action="" method="post">
     <?php
      $sql_query = "SELECT * FROM `courselist`";
      $result = $conn->query($sql_query);
      if($result) {
          $contentid = "content1";
          echo "<table id=\"coursetable\" border=0><tr><th>Coursename</th><th>SemStart</th><th>SemEnd</th></tr>";
          if ($result->num_rows > 0) {
              while($row = $result->fetch_assoc())
              {
                  echo "<tr><td>".$row['coursename']."</td>";
                  echo "<td>".$row['semstart']."</td>";
                  echo "<td>".$row['semend']."</td></tr>";
              }
              echo "</table>";
          }
          if(isset($_REQUEST['clickedvar'])) {
              if (isset($_REQUEST['addcoursetxt']) && $_REQUEST['semstart'] && $_REQUEST['semend']  && $_REQUEST['crssubmit1']) {
                  $coursename1 = $_REQUEST['addcoursetxt'];
                  $semstart1 = $_REQUEST['semstart'];
                  $semend1 = $_REQUEST['semend'];
                  $TABLEID = "courselist";
                  if($semstart1<=$semend1){
                      $sql_query = "SELECT * FROM `courselist` WHERE coursename = '$coursename1'";
                      $result = $conn->query($sql_query);
                      if ($result) {
                          if ($result->num_rows > 0) {
                              echo "<p class=\"errtext\">Exists</p>";
//                          $error = "EXISTS";
////                          echo "<input type=\"hidden\" name=\"errortext\" value=\"EXISTS\">";
//                          echo "<input type=\"hidden\" value=".$error." name=\"errortext\">";
                          } else {
                              $sql_query = "INSERT INTO `courselist`(coursename, semstart, semend) VALUES ('$coursename1', $semstart1, $semend1)";
                              if ($conn->query($sql_query) === TRUE) {
                                  echo "<p class=\"succtext\">New record created successfully</p>";
//                              echo "HERESUCC";
//                              echo "<input type=\"hidden\" name=\"succtext\" value=\"New record created successfully\">";
                              } else {
                              echo "<p class=\"succtext\">Error: \" . $sql . \"<br>\" . $conn->errors</p>";
                              }
                          }

                      }
                  }
                  else
                  {
                      echo "start sem must be less than end sem";
                  }

                  mysqli_close($conn);
              }
              else
              {
                  echo "</br></br>VALUES NOT SET";
              }
          }
      }
      ?>
          <table>
              <tr>
              <td>
                  Course name:-
              </td>
               <td>
                   <input type="text" name="addcoursetxt" required>
               </td>
              </tr>
              <tr>
              <td>
                  Start sem:-
                  </td>
                  <td>
                      <input type="number" name="semstart" max="10" min="1" required>
                  </td>
              </tr>
              <tr>
                  <td>
                      End sem:-
                  </td>
                  <td>
                      <input type="number" name="semend" min="1" max="10" required>
                  </td>
              </tr>
              <tr><td><input id="submit" type="submit" value="submit" name="crssubmit1"></br></td>

              </tr>
          </table>
         <input type="hidden" name="clickedvar">
      </form>
  </section>
  <section id="content2">

      <form action="" method="post">

          <input type="hidden" name="clickedvar2">
          <?php
          $sql_query = "SELECT * FROM `courselist`";
          $result = $conn->query($sql_query);
          if($result && !isset($_REQUEST['selcrssubmit'])  && !isset($_REQUEST['selsemsubmit'])) {
              $str = "STRING";
              if ($result->num_rows > 0) {
                  echo "<select name=\"selcrsname\">";
                  while ($row = $result->fetch_assoc()) {
                      echo "<option value=" . $row['courseid'] . ">" . $row['coursename'] . "</option>";
                  }
                  echo "<input type=\"submit\" value=\"submit\"name=\"selcrssubmit\"></selec>";
              }
          }
          if(isset($_REQUEST['selcrsname']) && !isset($_REQUEST['selsemsubmit']) && !isset($_REQUEST['subsubmit']))
          {
              $crsid = $_REQUEST['selcrsname'];
              $sql_query = "SELECT * FROM `courselist` WHERE courseid = $crsid";
              $result = $conn->query($sql_query);
              $row = $result->fetch_assoc();
              $semstr = $row['semstart'];
              $semend = $row['semend'];
              $crsname = $row['coursename'];
              echo "<p>Selected > ".$crsname."</p>";
              echo "<select name=\"selsemval\">";
              for($i=$semstr;$i<=$semend;$i++) {
                  echo "<option value=" . $i. ">" . $i . "</option>";
              }
              echo "<input type=\"hidden\" value=".$crsname." name=\"crsname\">";
              echo "<input type=\"hidden\" value=".$crsid." name=\"selcrsname\">";
              echo "<input type=\"submit\" value=\"Submit\"name=\"selsemsubmit\"></select>";
          }
          if(isset($_REQUEST['selsemval']) && isset($_REQUEST['crsname']) && isset($_REQUEST['selcrsname']) && !isset($_REQUEST['subsubmit']) )
          {
              $crsid = $_REQUEST['selcrsname'];
              $semval = $_REQUEST['selsemval'];
              $crsname = $_REQUEST['crsname'];
              echo "<p>Selected > ".$crsname." > ".$semval."</p>";
              echo "Lits of added subjects:-</br></br>";
              $sql_query = "SELECT * FROM `subjectlist` WHERE courseid = $crsid AND semval = $semval";
              $result = $conn->query($sql_query);
              while($row = $result->fetch_assoc()){echo $row['subjectname']."</br>";
              }
              echo "<input type=\"hidden\" value=".$crsid." name=\"selcrsname\">";
              echo "<input type=\"hidden\" value=".$semval." name=\"selsemval\">";
              echo "</br></br><input type=\"text\" name=\"subname\" required><input type=\"submit\" value=\"Add\"name=\"subsubmit\">";
          }
          if(isset($_REQUEST['selcrsname']) && isset($_REQUEST['subsubmit']) && isset($_REQUEST['selsemval']) && isset($_REQUEST['subname'])) {
              $crsid = $_REQUEST['selcrsname'];
              $semval = $_REQUEST['selsemval'];
              $subname = $_REQUEST['subname'];
              $sql_query = "INSERT INTO `subjectlist`(subjectname, courseid, semval) VALUES ('$subname', $crsid, $semval)";
              if ($conn->query($sql_query) === TRUE) {
                  echo "New record created successfully";
              } else {
                  echo "Error: " . $sql . "<br>" . $conn->error;
              }
          }

          ?>

      </form>
  </section>
  <section id="content3">
      <form action="" method="post" enctype="multipart/form-data">
     <?php
      echo "<input type=\"hidden\" name=\"clickedvar3\">";
      $sql_query = "SELECT * FROM `courselist`";
      $result = $conn->query($sql_query);
      if($result && !isset($_REQUEST['selcrssubmit1'])  && !isset($_REQUEST['selsemsubmit1']) && !isset($_REQUEST['subsubmit1'])) {
          if ($result->num_rows > 0) {
              echo "<select name=\"selcrsname1\">";
              while ($row = $result->fetch_assoc()) {
                  echo "<option value=" . $row['courseid'] . ">" . $row['coursename'] . "</option>";
              }
              echo "<input type=\"submit\" value=\"Submit\"name=\"selcrssubmit1\"></select></br></br></br></br>";
          }
      }
      if(isset($_REQUEST['selcrsname1']) && !isset($_REQUEST['selsemsubmit1']) && !isset($_REQUEST['subsubmit1'])  && !isset($_REQUEST['filesubmit1']))
      {
          $crsid = $_REQUEST['selcrsname1'];
          $sql_query = "SELECT * FROM `courselist` WHERE courseid = $crsid";
          $result = $conn->query($sql_query);
          $row = $result->fetch_assoc();
          $semstr = $row['semstart'];
          $semend = $row['semend'];
          $crsname = $row['coursename'];
          echo "<p>Selected > ".$crsname."</p>";
          echo "<select name=\"selsemval1\">";
          for($i=$semstr;$i<=$semend;$i++) {
              echo "<option value=" . $i. ">" . $i . "</option>";
          }
          echo "<input type=\"hidden\" value=".$crsname." name=\"crsname1\">";
          echo "<input type=\"hidden\" value=".$crsid." name=\"selcrsname1\">";
          echo "<input type=\"submit\" value=\"Submit\"name=\"selsemsubmit1\"></select>";
      }
      if(isset($_REQUEST['selsemval1'])  && isset($_REQUEST['crsname1']) && isset($_REQUEST['selcrsname1']) && !isset($_REQUEST['subsubmit1'])  && !isset($_REQUEST['filesubmit1']))
      {
          $crsid = $_REQUEST['selcrsname1'];
          $semval = $_REQUEST['selsemval1'];
          $crsname = $_REQUEST['crsname1'];
          echo "<p>Selected > ".$crsname." > ".$semval."</p>";
          $sql_query = "SELECT * FROM `subjectlist` WHERE courseid = $crsid AND semval = $semval";
          $result = $conn->query($sql_query);
          if ($result->num_rows > 0) {
              echo "<select name=\"selsubname1\">";
              while ($row = $result->fetch_assoc()) {
                  echo "<option value=" . $row['subjectid'] . ">" . $row['subjectname'] . "</option>";
              }
              echo "<input type=\"hidden\" value=".$crsname." name=\"crsname1\"><input type=\"hidden\" value=".$semval." name=\"semval1\"><input type=\"submit\" value=\"Submit\"name=\"subsubmit1\"></select>";
          }
          else{
              echo "No subjects added for this semester yet";

          }

      }
      if(isset($_REQUEST['selsubname1']) && isset($_REQUEST['crsname1']) && isset($_REQUEST['semval1']) && !isset($_REQUEST['filesubmit1'])) {
          $semval = $_REQUEST['semval1'];
          $crsname = $_REQUEST['crsname1'];
          $subid = $_REQUEST['selsubname1'];
          $sql_query = "SELECT * FROM `subjectlist` WHERE subjectid = $subid";
          $result = $conn->query($sql_query);
          $row = $result->fetch_assoc();
          $subname1 = $row['subjectname'];
          echo "<p>Selected > ".$crsname." > ".$semval." > ".$subname1."</p>";
          $sql_query = "SELECT * FROM `paperlist` WHERE subjectid = $subid";
          $result = $conn->query($sql_query);
          echo "</br></br>Papers in database</br></br>";
          echo "<table><tr><th>Year</th><th>Month</th></tr>";
          if ($result->num_rows > 0) {
              while ($row = $result->fetch_assoc()) {
                  echo "<tr><td>".$row['year']."</td><td>".$row['month']." "."</td>";
              }
              echo "</table>";
          }
          else{
              echo "No papers added for this subject yet</br></br></br>";

          }
          echo "</br></br>Select pdf to upload:
            <input type=\"file\" name=\"fileToUpload\" id=\"fileToUpload\">
           <table><tr><td>
            </td></tr><tr><td>Year</td><td>
          <input type=\"number\" name=\"yearsub\" min=\"2000\" max=\"2050\" required></td></tr><tr><td>Month</td><td>
          <input type=\"number\" name=\"monthsub\" min=\"1\" max=\"12\" required></td></tr><tr><td>
     
          <input type=\"submit\" value=\"Upload Paper\" name=\"filesubmit1\"></td></tr></table><input type=\"hidden\" name=\"clickedvar3\"></tr><tr>
          <input type=\"hidden\" value=" . $subid . " name=\"subid\" required>";

      }
      if (isset($_REQUEST['filesubmit1']) && isset($_REQUEST['yearsub']) && isset($_REQUEST['monthsub']) && isset($_REQUEST['subid'])) {

              $target_dir = "papers/";
              $year = $_REQUEST['yearsub'];
              $month = $_REQUEST['monthsub'];
          $subid = $_REQUEST['subid'];
              $target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
              $uploadOk = 1;
              $dupli = 1;
              $filename = $target_dir.$subid."_".$year."_".$month.".pdf";
              $imageFileType = pathinfo($target_file, PATHINFO_EXTENSION);
              // Check if file already exists
              if (file_exists($filename)) {
                  unlink($filename);
                  $dupli = 0;
                  //$uploadOk = 0;
              }
              if ($imageFileType != "pdf") {
                  echo "Sorry, only PDFs are allowed.";
                  $uploadOk = 0;
              }
              if ($uploadOk == 0) {
                  echo "Sorry, your file was not uploaded.";
              } else {
                  if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
                      rename($target_file,$filename);
                      echo "The file " . $filename . " has been uploaded.</br></br>";
                      if($dupli==1)
                      {
                          $sql_query = "INSERT INTO `paperlist` (year, month, subjectid, pathoffile) VALUES ($year,$month,$subid,'$filename')";
                          if ($conn->query($sql_query) === TRUE) {
                              echo "Paper added in DB too";
                          } else {
                              echo "Error: " . $sql . "<br>" . $conn->error;
                          }
                      }

                  } else {
                      echo "Sorry, there was an error uploading your file.";
                  }
              }
          } ?>
      </form>
  </section>
  <section id="content4">
  </section>
</body>
</html>
