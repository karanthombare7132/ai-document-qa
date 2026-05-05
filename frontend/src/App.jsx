import { useState } from "react";
import "./App.css";

function App() {
  const [file, setFile] = useState(null);
  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");
  const [summary, setSummary] = useState("");

  const uploadFile = async () => {
    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch("http://localhost:8080/api/document/upload", {
      method: "POST",
      body: formData,
    });

    const data = await response.text();
    alert(data);
  };

  const askQuestion = async () => {
    const response = await fetch(
      `http://localhost:8080/api/document/ask?question=${question}`
    );

    const data = await response.text();
    setAnswer(data);
  };

  const getSummary = async () => {
    const response = await fetch("http://localhost:8080/api/document/summary");
    const data = await response.text();
    setSummary(data);
  };

  return (
    <div className="container">
      <h1>AI Document Q&A</h1>

      <input type="file" onChange={(e) => setFile(e.target.files[0])} />
      <button onClick={uploadFile}>Upload PDF</button>

      <br /><br />

      <input
        type="text"
        placeholder="Ask question"
        value={question}
        onChange={(e) => setQuestion(e.target.value)}
      />
      <button onClick={askQuestion}>Ask</button>

      <p>{answer}</p>

      <button onClick={getSummary}>Get Summary</button>
      <p>{summary}</p>
    </div>
  );
}

export default App;