import { useState } from 'react'

interface ChatResponse {
    message: string;
    sources: any[];
}

interface ChatEntry {
    userQuestion: string;
    llmAnswer: string;
}

function ChatBox() {
    const [question, setQuestion] = useState("")
    const [history, setHistory] = useState<ChatEntry[]>([])
    const [errorMessage, setErrorMessage] = useState("")
    const [isLoading, setIsLoading] = useState(false)
    const [sources, setSources] = useState<any[]>([])

    async function askQuestion() {
        setErrorMessage("")
        if (!question.trim()) return
        setIsLoading(true)
        try {
            const response = await fetch("http://localhost:8080/api/chat", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ userQuery: question })
            })
            const data: ChatResponse = await response.json()
            if (!response.ok) {
                setErrorMessage("Error: " + data.message)
                return
            }

            setHistory((prev) => [...prev, { userQuestion: question, llmAnswer: data.message }])
            setSources(data.sources)
            setQuestion("")
        } catch (error) {
            console.error("[Chat] Network failure:", error)
            setErrorMessage("Could not reach the backend")
        } finally {
            setIsLoading(false)
        }
    }

    return (
        <section>
            <h2>2. Ask a Question</h2>
            <input
                type="text"
                placeholder="Ask a question..."
                value={question}
                onChange={(e) => setQuestion(e.target.value)}
            />
            <button onClick={askQuestion} disabled={isLoading}>
                {isLoading ? "Thinking..." : "Ask"}
            </button>

            <p>{errorMessage}</p>

            {
                history.map((entry, index) => (
                <div key={index}>
                    <p>Q: {entry.userQuestion}</p>
                    <p>A: {entry.llmAnswer}</p>
                    <hr />
                </div>
            ))}

            {
                sources.map((chunk) => (
                <div key={chunk.id}>
                    <p>Page {chunk.metadata.page_number}</p>
                    <p>{chunk.text}</p>
                </div>
            ))}
        </section>
    )
}

export default ChatBox