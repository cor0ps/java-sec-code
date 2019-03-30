<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>userlist</title>
    <script type="text/javascript">
        function XHR() {
            var xhr;
            try {xhr = new XMLHttpRequest();}
            catch(e) {
                var IEXHRVers =["Msxml3.XMLHTTP","Msxml2.XMLHTTP","Microsoft.XMLHTTP"];
                for (var i=0,len=IEXHRVers.length;i< len;i++) {
                    try {xhr = new ActiveXObject(IEXHRVers[i]);}
                    catch(e) {continue;}
                }
            }
            return xhr;
        }

        function send(){
            evil_input = document.getElementById("evil_input").value;
            var xhr = XHR();
            xhr.open("post","/xxe/api",true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState==4 && xhr.status==201) {
                    data = JSON.parse(xhr.responseText);
                    tip_area = document.getElementById("tip-area");
                    tip_area.value = data.task.search+data.task.value;
                }
            };
            xhr.setRequestHeader("Content-Type","application/json");
            xhr.send('{"search":"'+evil_input+'","value":"own"}');
        }
    </script>
</head>
<body>

<div>
    <textarea id="tip-area" width="100px" height="50px" disabled=""></textarea>
        <input id="evil_input" type="text" width="100px" height="50px" value="type sth!">
        <button class="btn btn-default" type="button" onclick="send()">Go!</button>


</div>


</body>
</html>

