import { useState } from 'react'

interface ChatResponse {
    message: string;
    sources: any[];
}

function ChatBox() {

    const [question, setQuestion] = useState("")
    const [answer, setAnswer] = useState("")
    const [isLoading, setIsLoading] = useState(false)
    const [sources, setSources] = useState<any[]>([])

    async function askQuestion() {
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
                setAnswer("Error: " + data.message)
                return
            }
            setAnswer(data.message)
            setSources(data.sources)
            setQuestion("")
        } catch (error) {
            console.error("[Chat] Network failure:", error)
            setAnswer("Could not reach the backend server.")
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
            <p>{answer}</p>
            {
                sources.map((chunk) => (
                    <div key={chunk.id}>
                        <p>Page {chunk.metadata.page_number}</p>
                        <p>{chunk.text}</p>
                    </div>
                ))
            }
        </section>
    )
}
export default ChatBox