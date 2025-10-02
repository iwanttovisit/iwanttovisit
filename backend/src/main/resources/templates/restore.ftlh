<#ftl encoding="UTF-8">
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Password resetting on IWantToVisit.org</title>
    <style>
        #body {
            display: flex;
            flex-direction: column;
            font-family: "Segoe UI", serif;
            align-items: center;
            background-color: #ecedef;
            font-size: 20px;
            height: 100%;
        }

        #div {
            display: block;
            flex-direction: column;
            margin: 10%;
            font-family: "Segoe UI", serif;
            background-color: white;
            padding: 3%;
            border-radius: 20px;
            color: black;
        }

        #button {
            background-color: #ecedef;
            border-radius: 10px;
            padding: 5px;
            color: white;
            text-decoration: none;
        }

        #a {
            text-decoration: none;
        }
    </style>
</head>
<body>
<div id="body">
    <div id="div">
        <h1 style="text-align: center">Password resetting</h1>
        <p>Hi, ${name}!</p>
        <p>We get password reset request on IWantToVisit.org from you. If it was
            really you, press the button below.</p>
        <p><a id="button" href="${link}${token}">Reset</a></p>
        <i>Or just open follow url if button is not working:<br> <a id="a"
                                                                    href="${link}${token}">${link}${token}</a></i>
        <p>Honestly, <br> your IWantToVisit team</p>
        <a id="a" href="${mainLink}">${mainDomainName}</a>
    </div>
</div>
</body>
</html>