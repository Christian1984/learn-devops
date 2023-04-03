const express = require("express");
const app = express();
const port = 3000;

app.get("/", (req, res) => {
  res.json([
    {
      name: "Bob",
      email: "bob@byom.de",
    },
  ]);
});

app.listen(port, () => {
  console.log(`User API listening on port ${port}`);
});
