<!DOCTYPE html>
<html>
<head>
</head>
<body>
    <h2>${repository.directory}</h2>
    <table>
        <thead>
            <tr>
                <th>Person Name</th>
                <th>Person ID</th>
            </tr>
        </thead>
        <tbody>
            <#list repository.documents as person, documents>
                <#list documents as document>
                    <tr>
                        <td>${person.name()}</td>
                        <td>${person.id()}</td>
                    </tr>
                </#list>
            </#list>
        </tbody>
    </table>

    <table>
            <thead>
                <tr>
                    <th>Document Name</th>
                    <th>File Type</th>
                </tr>
            </thead>
            <tbody>
                 <#list repository.documents as person, documents>
                    <#list documents as document>
                        <tr>
                            <td>${document.title()}</td>
                            <td>${document.fileType()}</td>
                        </tr>
                    </#list>
                </#list>
            </tbody>
        </table>
</body>
</html>
