Feature: stex search

  Scenario: Should properly be notified of stex messages

    Given alban subscribes to orders notifications

    And blotter system receives stex orders from AVQ
      | externalIdentifier                     | author | portfolio    | amount | instrument | intent | status    |
      | stex-ingestion-external-identifier-100 | peva   | PF-000000001 | 100000 | LU15000000 | buy    | validated |
      | stex-ingestion-external-identifier-200 | peva   | PF-000000001 | 800000 | LU16000000 | sell   | placed    |

    Then within PT10S, alban should be notified of the below orders notifications
      | externalIdentifier                     | metaType | author | portfolio    | amount | instrument | intent | status    |
      | stex-ingestion-external-identifier-100 | stex     | peva   | PF-000000001 | 100000 | LU15000000 | buy    | validated |
      | stex-ingestion-external-identifier-200 | stex     | peva   | PF-000000001 | 800000 | LU16000000 | sell   | placed    |


