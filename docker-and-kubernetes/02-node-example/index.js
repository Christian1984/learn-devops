const express = require("express");
const app = express();
const port = 3000;

app.get("/", (req, res) => {
  res.json([
    {
      name: "Bob",
      email: "bob@byom.de",
    },
    {
      name: "Alice",
      email: "alice@byom.de",
    },
    {
      name: "Jack",
      email: "jack@byom.de",
    },
  ]);
});

app.listen(port, () => {
  console.log(`User API listening on port ${port}`);
});
