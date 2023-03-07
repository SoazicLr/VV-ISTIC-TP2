# Code of your exercise
```xml
<ruleset name="Rules">
    <rule>
        <description>
            détecter l'utilisation d'au moins trois instructions if imbriquées dans les programmes Java 
        </description>
        <properties>
            <property>
                <value>
                    <![CDATA[ 
                    //IfStatement[descendant::IfStatement[descendant::IfStatement]]
                    ]]>
                </value>
            </property>
        </properties>
    </rule>
</ruleset>
```
