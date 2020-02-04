Feature: stex search

  Scenario: Should properly find stex messages

    Given blotter system receives the messages below
      | externalIdentifier                     | metaType | author | portfolio    | amount | instrument | intent |
      | stex-ingestion-external-identifier-100 | stex     | peva   | PF-000000001 | 100000 | LU15000000 | buy    |
      | stex-ingestion-external-identifier-200 | stex     | peva   | PF-000000001 | 800000 | LU16000000 | sell   |

    When choisel searches for orders by criteria
      | portfolio    | metaType |
      | PF-000000001 | stex     |

    Then within PT10S, the below orders should be found
      | externalIdentifier                     | metaType | author | portfolio    | amount | instrument | intent |
      | stex-ingestion-external-identifier-100 | stex     | peva   | PF-000000001 | 100000 | LU15000000 | buy    |
      | stex-ingestion-external-identifier-200 | stex     | peva   | PF-000000001 | 800000 | LU16000000 | sell   |

