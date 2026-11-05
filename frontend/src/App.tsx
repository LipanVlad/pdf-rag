import { useState } from 'react'
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom'
import PdfUploader from './components/PdfUploader'
import ChatBox from './components/ChatBox'
import DocumentList from './components/DocumentList'

function App() {
    const [refreshList, setRefreshList] = useState(0)

    return (
        <BrowserRouter>
            <div>
                <h1>PDF Assistant</h1>
                <nav style={{ display: "flex", gap: "15px", marginBottom: "20px" }}>
                    <Link to="/" children={"Manage Documents"} />
                    <Link to="/chat">Ask Questions</Link>
                </nav>
                <hr />
                <Routes>
                    <Route
                        path="/"
                        element={
                            <div>
                                <DocumentList refreshList={refreshList} />
                                <hr />
                                <PdfUploader onUploadSuccess={() => setRefreshList(refreshList + 1)} />
                            </div>
                        }
                    />

                    <Route
                        path="/chat"
                        element={<ChatBox />}
                    />
                </Routes>
            </div>
        </BrowserRouter>
    )
}

export default App