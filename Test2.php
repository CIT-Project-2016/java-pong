<html>
   <head>
   <title>php02_eg1</title>
      <style type="text/css">
      body{background-color:cornsilk;color:blue}
      </style>
   </head>
   <body>
      <h1>Home Page</h1>
      <h2>I hope you enjoy my fabulous Web Site!</h2>
      <p>There are many amazing things in this site, blah, blah, blah...</p>
      <br><br><br><br><br><br><br><br><br><br><br>
      <p>Hits:
      <?php
         // this php prints out a hit counter
         //first read the current hits from the file
         $FILE = "hits.txt";
         $hitsfile = fopen($FILE, "r") or die("unable to open $FILE");
         $hits = trim(fgets($hitsfile));
         fclose($hitsfile);
         //add 1 to the hits, write back to the file and display
         $hits=$hits+1;
         $hitsfile = fopen($FILE, "w") or die("unable to open $FILE");
         fwrite($hitsfile, $hits);
         fclose($hitsfile);
         echo $hits;
      ?>
   </body>
</html>