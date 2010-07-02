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
    </style>
</head>
<body>
<h1>Example Auto-Generated Concordion Test Overview Page</h1>

<p>
    <i> See <a href="http://code.google.com/p/badrobot/wiki/ConcordionAntTask">http://code.google.com/p/badrobot/wiki/ConcordionAntTask</a></i>
</p>

<p>
    The list of Concordion tests include the following, grouped in this example, by <i>iteration</i>. You can group
    your test by anything.
</p>

<#list tests?keys as iteration>
<h2>Iteration ${iteration}</h2>
<ul>
    <#list tests[iteration] as test>
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
<p class="idea">
    Note, the red and green styles are for illustration purposes only. Concordion will generate these once this overview
    page has been executed. They are here only to indicate what could have been...
</p>
</body>
</html>