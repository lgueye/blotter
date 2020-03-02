Feature: fx search

  Scenario: Should properly find fx messages

    Given blotter system receives fx orders from SMT
      | externalIdentifier                   | author  | portfolio    | amount | instrument | intent | status    |
      | fx-ingestion-external-identifier-700 | peva    | PF-000000003 | 500000 | LU12000000 | buy    | validated |
      | fx-ingestion-external-identifier-600 | peva    | PF-000000003 | 600000 | LU13000000 | sell   | placed    |
      | fx-ingestion-external-identifier-500 | mathieu | PF-000000004 | 700000 | LU14000000 | sell   | booked    |

    And blotter system receives stex orders from AVQ
      | externalIdentifier                     | author  | portfolio    | amount | instrument | intent | status |
      | stex-ingestion-external-identifier-500 | mathieu | PF-000000003 | 400000 | LU15000000 | sell   | placed |

    When alban searches for orders by criteria
      | portfolios    | metaTypes |
      | PF-000000003 | fx       |

    Then within PT5S, the below orders should be found
      | externalIdentifier                     | metaType | author | portfolio    | amount | instrument | intent | status    |
      | stex-ingestion-external-identifier-700 | fx       | peva   | PF-000000003 | 500000 | LU12000000 | buy    | validated |
      | stex-ingestion-external-identifier-600 | fx       | peva   | PF-000000003 | 600000 | LU13000000 | sell   | placed    |

