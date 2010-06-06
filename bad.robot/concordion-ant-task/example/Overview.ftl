<html xmlns:concordion="http://www.concordion.org/2007/concordion">
<head>
    <title>Overview</title>
    <link href="style.css" rel="stylesheet" type="text/css" />    
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
            <li class="ignored">
                <a href="${test.locationAsRelativeUrl}">${test.title}</a> (ignored tests show up differently)
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