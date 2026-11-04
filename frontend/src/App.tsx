import PdfUploader from './components/PdfUploader'
import ChatBox from './components/ChatBox'
import DocumentList from "./components/DocumentList.tsx";
import {useState} from "react";

function App() {
    const [refreshList, setRefreshList] = useState(0)

    return (
        <div>
            <h1>PDF Assistant</h1>
            <DocumentList refreshList={refreshList} />
            <hr />
            <PdfUploader onUploadSuccess={() => setRefreshList(refreshList + 1)} />
            <hr />
            <ChatBox />
        </div>
    )
}

export default App

