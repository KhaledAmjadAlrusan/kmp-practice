//
//  ListView.swift
//  iosApp
//
//  Created by Khaled Alrusan on 2025-07-22.
//

import SwiftUI
import KMPNativeCoroutinesAsync
import KMPObservableViewModelSwiftUI
import Shared

struct ListView: View {
    @ObservedViewModel
    var viewModel: ListViewModel

    init() {
        _viewModel = ObservedViewModel(wrappedValue: ListViewModel(
            museumRepository: KoinDependencies().museumRepository
        ))
    }

    
    let columns = [
        GridItem(.adaptive(minimum: 120), alignment: .top)
    ]

    var body: some View {

        Group {
            switch viewModel.state {
            case is ListViewState.Loading:
                ProgressView()
            case let error as ListViewState.Error:
                Text(error.message)
            case let content as ListViewState.Content:
                NavigationStack {
                    ScrollView {
                        LazyVGrid(columns: columns, spacing: 20) {
                            museumGridItems(for: content.museums)
                        }
                    }
                }

            default:
                Text("Unknown state")
            }
        }.onAppear{
            viewModel.dispatch(intent: ListIntent.LoadMuseums())
        }
    }
}

struct ObjectFrame: View {
    let obj: MuseumObject

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            GeometryReader { geometry in
                AsyncImage(url: URL(string: obj.primaryImageSmall)) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                    case .success(let image):
                        image
                            .resizable()
                            .scaledToFill()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                            .clipped()
                            .aspectRatio(1, contentMode: .fill)
                    default:
                        EmptyView()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                    }
                }
            }
            .aspectRatio(1, contentMode: .fit)

            Text(obj.title)
                .font(.headline)

            Text(obj.artistDisplayName)
                .font(.subheadline)

            Text(obj.objectDate)
                .font(.caption)
        }
    }
}

@ViewBuilder
private func museumGridItems(for items: [MuseumObject]) -> some View {
    ForEach(items, id: \.self) { item in
        NavigationLink(destination: DetailView(objectId: item.objectID)) {
            ObjectFrame(obj: item)
        }
        .buttonStyle(PlainButtonStyle())
    }
}
