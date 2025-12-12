Зозуля Марія  ІС-34 варіант 4

C3 = 1 Кільцевий однонаправлений список. Останній елемент посилається на перший.

C5 = 4 Шістнадцяткова (Hexadecimal, Base 16).

C5_add = 0 Двійкова (Binary, Base 2).

C7 = 4 Залишок від ділення (Modulo %). Реалізовано в `additionalOperation`.

РЕЗУЛЬТАТ

PS D:\test-assignment> java -jar lib/junit-platform-console-standalone-6.0.1.jar execute -cp bin --scan-classpath

Thanks for using JUnit! Support its development at https://junit.org/sponsoring

груд. 12, 2025 9:00:34 ПП org.junit.platform.launcher.core.DiscoveryIssueNotifier logIssues
INFO: TestEngine with ID 'junit-vintage' encountered a non-critical issue during test discovery:

(1) [INFO] The JUnit Vintage engine is deprecated and should only be used temporarily while migrating tests to JUnit Jupiter or another testing framework with native JUnit Platform support.
.
+-- JUnit Platform Suite [OK]
+-- JUnit Jupiter [OK]
'-- JUnit Vintage [OK]
  +-- ScaleOfNotationChangeTest [OK]
  | +-- testToOctal [A] got: <false>, expected: is <true>
  | +-- testToBinary [OK]
  | +-- testToHex [A] got: <false>, expected: is <true>
  | +-- testToDecimal [A] got: <false>, expected: is <true>
  | '-- testToTernary [A] got: <false>, expected: is <true>
  +-- AdditionalOperationTest [OK]
  | +-- testAND [A] got: <false>, expected: is <true>
  | +-- testAdd [A] got: <false>, expected: is <true>
  | +-- testDiv [A] got: <false>, expected: is <true>
  | +-- testMod [OK]
  | +-- testOR [A] got: <false>, expected: is <true>
  | +-- testMultiply [A] got: <false>, expected: is <true>
  | '-- testRemove [A] got: <false>, expected: is <true>
  +-- FileListTest [OK]
  | +-- testWriteLongList [OK]
  | +-- testToString [OK]
  | +-- testEmptyFile [OK]
  | +-- testIncorrectFileName [OK]
  | +-- testFileExists [OK]
  | +-- testWriteSingleDigit [OK]        
  | '-- testReadSingleDigit [OK]
  '-- StringListTest [OK]
    +-- testToString [OK]
    +-- testInvalidStringInput [OK]      
    '-- testSingleDigit [OK]

Test run finished after 178 ms
[         7 containers found      ]      
[         0 containers skipped    ]      
  | +-- testWriteSingleDigit [OK]   
  | '-- testReadSingleDigit [OK]    
  '-- StringListTest [OK]
    +-- testToString [OK]
    +-- testInvalidStringInput [OK] 
    '-- testSingleDigit [OK]        

Test run finished after 178 ms      
[         7 containers found      ] 
[         0 containers skipped    ] 
[         7 containers started    ] 
[         0 containers aborted    ] 
[         7 containers successful ] 
[         0 containers failed     ] 
[        22 tests found           ] 
[         0 tests skipped         ] 
[        22 tests started         ] 
[        10 tests aborted         ] 
[        12 tests successful      ] 
[         0 tests failed          ] 
  '-- StringListTest [OK]
    +-- testToString [OK]
    +-- testInvalidStringInput [OK] 
    '-- testSingleDigit [OK]        

Test run finished after 178 ms      
[         7 containers found      ] 
[         0 containers skipped    ] 
[         7 containers started    ] 
[         0 containers aborted    ] 
[         7 containers successful ] 
[         0 containers failed     ] 
[        22 tests found           ] 
[         0 tests skipped         ] 
[        22 tests started         ] 
[        10 tests aborted         ] 
[        12 tests successful      ] 
[         0 tests failed          ] 
    '-- testSingleDigit [OK]        

Test run finished after 178 ms      
[         7 containers found      ] 
[         0 containers skipped    ] 
[         7 containers started    ] 
[         0 containers aborted    ] 
[         7 containers successful ] 
[         0 containers failed     ] 
[        22 tests found           ] 
[         0 tests skipped         ] 
[        22 tests started         ] 
[        10 tests aborted         ] 
[        12 tests successful      ] 
[         0 tests failed          ] 
[         0 containers skipped    ] 
[         7 containers started    ] 
[         0 containers aborted    ] 
[         7 containers successful ] 
[         0 containers failed     ] 
[        22 tests found           ] 
[         0 tests skipped         ] 
[        22 tests started         ] 
[        10 tests aborted         ] 
[        12 tests successful      ] 
[         0 tests failed          ] 
[         7 containers successful ] 
[         0 containers failed     ] 
[        22 tests found           ] 
[         0 tests skipped         ] 
[        22 tests started         ] 
[        10 tests aborted         ] 
[        12 tests successful      ] 
[         0 tests failed          ] 
[         0 tests skipped         ] 
[        22 tests started         ] 
[        10 tests aborted         ] 
[        12 tests successful      ] 
[         0 tests failed          ] 
[        10 tests aborted         ] 
[        12 tests successful      ] 
[         0 tests failed          ]
