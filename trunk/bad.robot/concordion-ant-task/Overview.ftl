<html xmlns:concordion="http://www.concordion.org/2007/concordion">
<head>
    <title>Overview</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
    <style>
        p.success {
            padding: 2px;
        }

        .success, .success * {
            background-color: #afa !important;
        }

        .success pre {
            background-color: #bbffbb;
        }

        .failure, .failure * {
            background-color: #ffb0b0;
            padding: 1px;
        }

        .special {
            font-style: italic;
        }        
    </style>
</head>
<body>
<h1>Concordion Ant Task User Manual</h1>

<p>
    <i> See <a href="http://code.google.com/p/badrobot/wiki/ConcordionAntTask">http://code.google.com/p/badrobot/wiki/ConcordionAntTask</a></i>
</p>

<p>
    ...
</p>

<#list tests?keys as group>
<h2>${group}</h2>
<ul>
    <#list tests[group] as test>
        <#if test.ignore || test.duplicate>
            <li>
                <a class="ignored" href="${test.locationAsRelativeUrl}">${test.title}</a> (ignored tests show up differently)
            </li>
            <#else>
            <li>
                <a concordion:run="concordion" href="${test.locationAsRelativeUrl}">${test.title}</a>
            </li>
        </#if>
    </#list>
</ul>
</#list>
</body>
</html>