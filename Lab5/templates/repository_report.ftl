<!DOCTYPE html>
<html>
<head>
    <title>Repository Report</title>
</head>
<body>
    <h1>Repository Report</h1>
    <h2>Repository Directory: ${repository.directory}</h2>
    <table>
        <thead>
            <tr>
                <th>Person ID</th>
                <th>Person Name</th>
                <th>Document Title</th>
                <th>File Type</th>
            </tr>
        </thead>
        <tbody>
            <#list repository.documents as person, documents>
                <#list documents as document>
                    <tr>
                        <td>${person.id}</td>
                        <td>${person.name}</td>
                        <td>${document.title}</td>
                        <td>${document.fileType}</td>
                    </tr>
                </#list>
            </#list>
        </tbody>
    </table>
</body>
</html>
