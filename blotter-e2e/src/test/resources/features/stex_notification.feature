Feature: stex search

  Scenario: Should properly be notified of stex messages

    Given choisel subscribes to orders notifications

    When blotter system receives the messages below
      | externalIdentifier                     | metaType | author | portfolio    | amount | instrument | intent |
      | stex-ingestion-external-identifier-100 | stex     | peva   | PF-000000001 | 100000 | LU15000000 | buy    |
      | stex-ingestion-external-identifier-200 | stex     | peva   | PF-000000001 | 800000 | LU16000000 | sell   |


    Then within PT10S, choisel should be notified of the below orders notifications
      | externalIdentifier                     | metaType | author | portfolio    | amount | instrument | intent |
      | stex-ingestion-external-identifier-100 | stex     | peva   | PF-000000001 | 100000 | LU15000000 | buy    |
      | stex-ingestion-external-identifier-200 | stex     | peva   | PF-000000001 | 800000 | LU16000000 | sell   |


