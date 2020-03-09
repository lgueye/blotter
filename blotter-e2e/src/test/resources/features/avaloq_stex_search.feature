Feature: stex search

  Scenario: Should properly find stex messages

    Given blotter system receives stex orders from AVQ
      | externalIdentifier                     | author  | portfolio    | price  | instrument | intent | status    | settlementDate           |
      | stex-ingestion-external-identifier-100 | peva    | PF-000000001 | 100000 | LU15000000 | buy    | validated | 2020-03-15T00:00:00.000Z |
      | stex-ingestion-external-identifier-200 | peva    | PF-000000001 | 800000 | LU16000000 | sell   | placed    | 2020-03-16T00:00:00.000Z |
      | stex-ingestion-external-identifier-400 | mathieu | PF-000000002 | 600000 | LU17000000 | sell   | booked    | 2020-03-17T00:00:00.000Z |

    And blotter system receives fx orders from AVQ
      | externalIdentifier                     | author  | portfolio    | price  | instrument | intent | status | settlementDate           |
      | stex-ingestion-external-identifier-300 | mathieu | PF-000000001 | 800000 | LU19000000 | buy    | placed | 2020-03-18T00:00:00.000Z |

    When alban searches for orders by criteria
      | portfolios   | metaTypes |
      | PF-000000001 | stex      |

    Then within PT5S, the below orders should be found
      | externalIdentifier                     | metaType | author | portfolio    | price  | instrument | intent | status    | settlementDate           |
      | stex-ingestion-external-identifier-100 | stex     | peva   | PF-000000001 | 100000 | LU15000000 | buy    | validated | 2020-03-15T00:00:00.000Z |
      | stex-ingestion-external-identifier-200 | stex     | peva   | PF-000000001 | 800000 | LU16000000 | sell   | placed    | 2020-03-16T00:00:00.000Z |

