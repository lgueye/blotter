Feature: stex search

  Scenario: Should properly find stex messages

    Given blotter system receives stex orders from AVQ
      | externalIdentifier                     | author  | portfolio    | price  | instrument | intent | status    |
      | stex-ingestion-external-identifier-100 | peva    | PF-000000001 | 100000 | LU15000000 | buy    | validated |
      | stex-ingestion-external-identifier-200 | peva    | PF-000000001 | 800000 | LU16000000 | sell   | placed    |
      | stex-ingestion-external-identifier-400 | mathieu | PF-000000002 | 600000 | LU17000000 | sell   | booked    |

    And blotter system receives fx orders from AVQ
      | externalIdentifier                     | author  | portfolio    | price  | instrument | intent | status |
      | stex-ingestion-external-identifier-300 | mathieu | PF-000000001 | 800000 | LU19000000 | buy    | placed |

    When alban searches for orders by criteria
      | portfolios   | metaTypes |
      | PF-000000001 | stex      |

    Then within PT5S, the below orders should be found
      | externalIdentifier                     | metaType | author | portfolio    | price  | instrument | intent | status    |
      | stex-ingestion-external-identifier-100 | stex     | peva   | PF-000000001 | 100000 | LU15000000 | buy    | validated |
      | stex-ingestion-external-identifier-200 | stex     | peva   | PF-000000001 | 800000 | LU16000000 | sell   | placed    |

