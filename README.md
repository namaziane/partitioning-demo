"# partitioning-demo"
UserController    ->     UserService       ->     EntityManager     ->    PartitionAwareInsertListener
|                         |                           |                              |
1. Receives Request          2. @Transactional Begins    3. PreInsert Event Triggered   4. Adds Partition Key
   |                         |                           |                              |
   v                         v                           v                              v
   ----------------------------------------------------------------------------------------
                                Persist User
   ----------------------------------------------------------------------------------------
   |                         |                           |                              |
   v                         v                           v                              v
5. Returns Response          6. Transaction Commits      7. Persist Event Triggered     8. Adjust Partition Filter
