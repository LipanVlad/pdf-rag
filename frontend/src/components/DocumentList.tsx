import { useState, useEffect } from 'react'

interface PdfDocumentResponse {
    id: number;
    fileName: string;
    fileSize: number;
    uploadTimestamp: string;
    chunkCount: number;
}
interface DocumentListProps {
    refreshList: number;
}

function DocumentList({ refreshList }: DocumentListProps) {
    const [documents, setDocuments] = useState<PdfDocumentResponse[]>([])
    const [deleteMessage, setDeleteMessage] = useState<String>("")
    async function loadDocuments() {
        const response = await fetch("http://localhost:8080/api/documents")
        const data: PdfDocumentResponse[] = await response.json()
        setDocuments(data)
    }
    useEffect(() => {
        loadDocuments()
    }, [refreshList])

    async function deleteDoc(deletedDocId:number){
        const response = await fetch("http://localhost:8080/api/documents/" + deletedDocId, {
            method: "DELETE"
        })
        const data:String = await response.text();
        if(!response.ok){
            setDeleteMessage("Error!");
            return;
        }
        setDeleteMessage(data)
        setDocuments(documents.filter((doc) => {return doc.id !== deletedDocId}))
    }

    return (
        <section>
            <h2>Uploaded Documents ({documents.length})</h2>
            {documents.map((doc) => (
                <div key={doc.id}>
                    <p>{doc.fileName} — {doc.chunkCount} chunks</p>
                    <button onClick={() => deleteDoc(doc.id)}>
                        Delete
                    </button>
                </div>
            ))}
            <p>{deleteMessage}</p>
        </section>
    )
}
export default DocumentList