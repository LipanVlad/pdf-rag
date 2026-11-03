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
    useEffect(() => {
        async function loadDocuments() {
            const response = await fetch("http://localhost:8080/api/documents")
            const data: PdfDocumentResponse[] = await response.json()
            setDocuments(data)
        }

        loadDocuments()
    }, [refreshList])
    return (
        <section>
            <h2>Uploaded Documents ({documents.length})</h2>
            {documents.map((doc) => (
                <div key={doc.id}>
                    <p>{doc.fileName} — {doc.chunkCount} chunks</p>
                </div>
            ))}
        </section>
    )
}
export default DocumentList