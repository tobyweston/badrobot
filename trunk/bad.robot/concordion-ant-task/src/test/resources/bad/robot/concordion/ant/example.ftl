<html>
<head>
    <title>Overview</title>
</head>
<body>
<h1>Tests</h1>

<p>The list of tests include the following</p>
    <#list tests?keys as group>
    <h2>Group ${group}</h2>
    <ul>
    <#list tests[group] as test>
        <li><a href="${test.locationAsRelativeUrl}" ignore="${test.ignore?string}" location="${test.location}" title="${test.title}" group="${test.group}" duplicate="${test.duplicate?string}">${test.title}</a></li>
    </#list>
    </ul>
    </#list>
</body>
</html>