jboss_hornetq_clustering
========================


Fist follow
https://docs.jboss.org/author/display/AS71/AS7+Cluster+Howto

Then in the Master configure the domain.xml (profile - full-ha part!) for the things marked yellow

               <subsystem xmlns="urn:jboss:domain:messaging:1.1">
                <hornetq-server>
                    <clustered>true</clustered>
                    <persistence-enabled>true</persistence-enabled>
                      <cluster-user>jms-user</cluster-user>
                    <cluster-password>simple-pass</cluster-password>
                    <journal-file-size>102400</journal-file-size>
                    <journal-min-files>2</journal-min-files>

                    <connectors>
                        <netty-connector name="netty" socket-binding="messaging"/>
                        <netty-connector name="netty-throughput" socket-binding="messaging-throughput">
                            <param key="batch-delay" value="50"/>
                        </netty-connector>
                        <in-vm-connector name="in-vm" server-id="0"/>
                    </connectors>

                    <acceptors>
                        <netty-acceptor name="netty" socket-binding="messaging"/>
                        <netty-acceptor name="netty-throughput" socket-binding="messaging-throughput">
                            <param key="batch-delay" value="50"/>
                            <param key="direct-deliver" value="false"/>
                        </netty-acceptor>
                        <in-vm-acceptor name="in-vm" server-id="0"/>
                    </acceptors>

                    <broadcast-groups>
                        <broadcast-group name="bg-group1">
                            <group-address>231.7.7.7</group-address> 
                            <group-port>9876</group-port>
                            <broadcast-period>5000</broadcast-period>
                            <connector-ref>
                                netty
                            </connector-ref>
                        </broadcast-group>
                    </broadcast-groups>

                    <discovery-groups>
<!-- 5445 port is used in connecting , also edit your /etc/hosts file in case host is not resolved-->
                        <discovery-group name="dg-group1">
                            <group-address>231.7.7.7</group-address>
                            <group-port>9876</group-port>
                            <refresh-timeout>10000</refresh-timeout>
                        </discovery-group>
                    </discovery-groups>

                    <cluster-connections>
                        <cluster-connection name="my-cluster">
                            <address>jms</address>
                            <connector-ref>netty</connector-ref>
                            <discovery-group-ref discovery-group-name="dg-group1"/>
                        </cluster-connection>
                    </cluster-connections>

                    <security-settings>
                        <security-setting match="#">
                            <permission type="send" roles="guest"/>
                            <permission type="consume" roles="guest"/>
                            <permission type="createNonDurableQueue" roles="guest"/>
                            <permission type="deleteNonDurableQueue" roles="guest"/>
                        </security-setting>
                    </security-settings>

                    <address-settings>
                        <address-setting match="#">
                            <dead-letter-address>jms.queue.DLQ</dead-letter-address>
                            <expiry-address>jms.queue.ExpiryQueue</expiry-address>
                            <redelivery-delay>0</redelivery-delay>
                            <max-size-bytes>10485760</max-size-bytes>
                            <address-full-policy>BLOCK</address-full-policy>
                            <message-counter-history-day-limit>10</message-counter-history-day-limit>
                            <redistribution-delay>1000</redistribution-delay>
                        </address-setting>
                    </address-settings>

                    <jms-connection-factories>
                        <connection-factory name="InVmConnectionFactory">
                            <connectors>
                                <connector-ref connector-name="in-vm"/>
                            </connectors>
                            <entries>
                                <entry name="java:/ConnectionFactory"/>
                            </entries>
                        </connection-factory>
                        <connection-factory name="RemoteConnectionFactory">
                            <connectors>
                                <connector-ref connector-name="netty"/>
                            </connectors>
                            <entries>
                                <entry name="RemoteConnectionFactory"/>
                                <entry name="java:jboss/exported/jms/RemoteConnectionFactory"/>
                            </entries>
                        </connection-factory>
                        <pooled-connection-factory name="hornetq-ra">
                            <transaction mode="xa"/>
                            <connectors>
                                <connector-ref connector-name="in-vm"/>
                            </connectors>
                            <entries>
                                <entry name="java:/JmsXA"/>
                            </entries>
                        </pooled-connection-factory>
                    </jms-connection-factories>

                    <jms-destinations>
                        <jms-queue name="testQueue">
                            <entry name="queue/test"/>
                            <entry name="java:jboss/exported/jms/queue/test"/>
                        </jms-queue>
                        <jms-queue name="MROQueue"> 
                            <entry name="queue/MROQueue"/>
                            <entry name="java:jboss/exported/jms/queue/MROQueue"/>
                        </jms-queue>
                        <jms-topic name="testTopic">
                            <entry name="topic/test"/>
                            <entry name="java:jboss/exported/jms/topic/test"/>
                        </jms-topic>
                        <jms-topic name="MROTopic">
                            <entry name="topic/MROTopic"/>
                            <entry name="java:jboss/exported/jms/topic/MROTopic"/>
                        </jms-topic>
                    </jms-destinations>
                </hornetq-server>
            </subsystem>

               <server-groups>
                       <server-group name="main-server-group" profile="full">
                           <jvm name="default">
                               <heap size="64m" max-size="512m"/>
                           </jvm>
                           <socket-binding-group ref="full-sockets"/>
                       </server-group>
                       <server-group name="other-server-group" profile="full-ha">
                           <jvm name="default">
                               <heap size="64m" max-size="512m"/>
                           </jvm>
                           <socket-binding-group ref="full-ha-sockets"/> <!-- This seems to be a bug and to get to this took me days -->
                           <deployments>
                               <deployment name="MroControllerEar.ear" runtime-name="MroControllerEar.ear"/>
                               <deployment name="MroExecutorEar.ear" runtime-name="MroExecutorEar.ear"/>
                           </deployments>
                       </server-group>
                   </server-groups>

Ports opened in the firewall

     tcp 8080   0.0.0.0/0
     tcp 5695   0.0.0.0/0
     udp 9876   0.0.0.0/0
     tcp 5445    0.0.0.0/0 // For hornetq to connet to each othe in cluste mode
     tcp 4447   0.0.0.0/0
     tcp 9999   0.0.0.0/0
     tcp 9876   0.0.0.0/0
     tcp 9990   0.0.0.0/0//Admin console
     tcp 8330   0.0.0.0/0 // There is a port offset : to access rest
     tcp 22     0.0.0.0/0 //ssh


Invocation

http://<MasterIP>:8330/MroControllerREST/rest/sessionbean/startmro?scopename=werwerwe
http://<slaveIP>:8330/MroControllerREST/rest/sessionbean/startmro?scopename=werwerwe

Will create a message/messages and send to MROQueue ; The MROExecutorMDB will receive it and post a message to MROTopic which will be received by MROControllerMDB in both the master and slave nodes

(Deploy MROController.ear and MROExecuotor.ear to only other-server-group (jboss-cli.sh , connect to master and deploy)


