import { useState } from 'react'

interface DocumentUploadResponse {
    fileName: string;
    chunkCount: number;
    message: string;
}
interface PdfUploaderProps {
    onUploadSuccess: () => void;
}
function PdfUploader({ onUploadSuccess }: PdfUploaderProps) {
    const [selectedFile, setSelectedFile] = useState<File | null>(null)
    const [uploadStatus, setUploadStatus] = useState("")
    const [isUploading, setIsUploading] = useState(false)

    async function handleUpload() {
        if (!selectedFile) return
        setIsUploading(true)
        const formData = new FormData()
        formData.append("file", selectedFile)
        try {
            const response = await fetch("http://localhost:8080/api/documents/upload", {
                method: "POST",
                body: formData
            })
            const data: DocumentUploadResponse = await response.json()
            if (!response.ok) {
                setUploadStatus("Error: " + data.message)
                return
            }
            setUploadStatus(data.message)
            onUploadSuccess()
        } catch (error) {
            console.error("[Upload] Network failure:", error)
            setUploadStatus("Could not reach the backend server.")
        } finally {
            setIsUploading(false)
        }
    }

    return (
        <section>
            <h2>1. Upload a PDF</h2>

            <input
                type="file"
                accept="application/pdf"
                onChange={(e) => setSelectedFile(e.target.files?.[0] || null)}
            />

            <button onClick={handleUpload} disabled={!selectedFile}>
                {isUploading ? "Uploading..." : "Upload PDF"}
            </button>

            <p>{uploadStatus}</p>
        </section>
    )
}

export default PdfUploader